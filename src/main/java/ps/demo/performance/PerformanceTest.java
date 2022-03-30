package ps.demo.performance;

import org.springframework.core.ParameterizedTypeReference;
import ps.demo.util.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PerformanceTest {

    public static void main(String[] args) {

        double[] tps = new double[] {0.1d};
        int callsPerStage = 5;

        QpsCallTemplate qpsCallTemplate = new QpsCallTemplate(tps, callsPerStage);
        List<QpsCall<ReqObj>> list = new ArrayList<>();
        for (int i = 0; i < tps.length * callsPerStage; i++) {
            ReqObj reqObj = new ReqObj();
            reqObj.setIndex(i);
            reqObj.setNum(i*i);
            QpsCall<ReqObj> qpsCall = new QpsCall<ReqObj>(reqObj) {
                @Override
                public ReqObj call() throws Exception {
                    System.out.println(Thread.currentThread().getName()+"-->>calling..."+reqObj.getIndex());
                    reqObj.setFileStr(MyStringUtil.randomAlphabetic(200*1024*1024));
                    String result = null;
                    try {
                        result = MyRestTemplateUtil.getInstance().postJsonObjectForString("http://localhost:8080/api/upload/hi", MyJsonUtil.object2Json(reqObj));
                    } catch (Exception ex) {
                        result = ex.getMessage();
                    }
                    reqObj.setResultStr(result);
                    return reqObj;
                }
            };
            list.add(qpsCall);
        }

        List<ReqObj> reqObjList = qpsCallTemplate.invoke(list);

        for (ReqObj reqObj : reqObjList) {
            reqObj.setFileStr("...");
        }
        System.out.println("--->>Result="+reqObjList);
        System.out.println("--->>Done!");

        MyReadWriteUtil.writeToFileTsInHomeDir(reqObjList);
        qpsCallTemplate.shutdown();

    }

}
