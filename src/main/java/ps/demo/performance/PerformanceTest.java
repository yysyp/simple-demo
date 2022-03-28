package ps.demo.performance;

import org.springframework.core.ParameterizedTypeReference;
import ps.demo.util.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PerformanceTest {

    public static void main(String[] args) {

        int[] tps = new int[] {1};
        int callsPerStage = 1;

        QpsCallTemplate qpsCallTemplate = new QpsCallTemplate(tps, callsPerStage);
        List<QpsCall<ReqObj>> list = new ArrayList<>();
        for (int i = 0; i < tps.length * callsPerStage; i++) {
            ReqObj reqObj = new ReqObj();
            QpsCall<ReqObj> qpsCall = new QpsCall<ReqObj>(reqObj) {
                @Override
                public ReqObj call() throws Exception {
                    System.out.println("-->>calling");
                    reqObj.setFileStr(MyStringUtil.randomAlphabetic(211*1024*1024));
                    String result = MyRestTemplateUtil.getInstance().postJsonObjectForString("http://localhost:8080/api/upload/hi", MyJsonUtil.object2Json(reqObj));
                    reqObj.setResultStr(result);
                    return reqObj;
                }
            };
            list.add(qpsCall);
        }

        List<ReqObj> reqObjList = qpsCallTemplate.invoke(list);

        System.out.println("--->>Done!");

        MyReadWriteUtil.writeToFileTsInHomeDir(reqObjList);
        qpsCallTemplate.shutdown();

    }

}
