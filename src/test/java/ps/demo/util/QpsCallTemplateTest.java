package ps.demo.util;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import lombok.*;
import org.slf4j.LoggerFactory;
import ps.demo.performance.ReqObj;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class QpsCallTemplateTest {


    public static void main(String[] args) {
        setLogLevel();

        double[] tps = new double[] {10, 25, 50};//new double[] {0.1d};
        int callsPerStage = 10;

        QpsCallTemplate qpsCallTemplate = new QpsCallTemplate(tps, callsPerStage);
        List<QpsCall<ps.demo.performance.ReqObj>> list = new ArrayList<>();
        for (int i = 0; i < tps.length * callsPerStage; i++) {
            ps.demo.performance.ReqObj reqObj = new ps.demo.performance.ReqObj();
            reqObj.setIndex(i);
            reqObj.setNum(5);
            QpsCall<ps.demo.performance.ReqObj> qpsCall = new QpsCall<ps.demo.performance.ReqObj>(reqObj) {
                @Override
                public ps.demo.performance.ReqObj call() throws Exception {
                    System.out.println(Thread.currentThread().getName()+"-->>Requesting..."+reqObj.getIndex());
                    reqObj.setFileStr(MyStringUtil.randomAlphabetic(32));
                    String result = null;
                    try {
                        result = MyRestTemplateUtil.getInstance().postJsonObjectForString("http://localhost:8080/api/upload/hi", MyJsonUtil.object2Json(reqObj));
                    } catch (Exception ex) {
                        result = ex.getMessage();
                    }
                    reqObj.setResultStr(result);
                    System.out.println("<<--Result:"+reqObj);
                    return reqObj;
                }
            };
            list.add(qpsCall);
        }

        List<ps.demo.performance.ReqObj> reqObjList = qpsCallTemplate.invoke(list);

        for (ps.demo.performance.ReqObj reqObj : reqObjList) {
            reqObj.setFileStr("...");
        }
        System.out.println("--->>Result="+reqObjList.size());
        System.out.println("--->>Done!");

        MyReadWriteUtil.writeToFileTsInHomeDir(reqObjList);
        qpsCallTemplate.shutdown();

    }

    private static void setLogLevel() {
    /*
    System.setProperty("org.apache.commons.logging.Log",
            "org.apache.commons.logging.impl.NoOpLog");
    org.apache.commons.logging.Log log = LogFactory.getLog("");
    log.info("You do not want to see me");
    System.out.println("Logger using is: "+LogFactory.getLog(""));
    */
        String[] names = { "org.apache", "org.springframework" };
        for (String ln : names) {
            // Try java.util.logging as backend
            java.util.logging.Logger.getLogger(ln).setLevel(java.util.logging.Level.INFO);
            // Try Log4J as backend
            //org.apache.log4j.Logger.getLogger(ln).setLevel(org.apache.log4j.Level.INFO);
            // Try another backend
            //Log4JLoggerFactory.getInstance().getLogger(ln).setLevel(java.util.logging.Level.INFO);
            // Try Logback
            Logger logback = new ch.qos.logback.classic.LoggerContext().getLogger(ln);
            logback.setLevel(Level.INFO);
            //Try Slf4j
            Logger restClientLogger = (Logger) LoggerFactory.getLogger(ln);
            restClientLogger.setLevel(Level.INFO);
        }
    }


    @Getter
    @Setter
    @ToString
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode(callSuper = false)
    static class ReqObj {

        private int index;

        private String fileStr;

        private int num;

        private long length;

        private String resultStr;


    }
}