package ps.demo.controller;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import ps.demo.util.MyFileUtil;
import ps.demo.util.MyPfTiming;
import ps.demo.util.MyReadWriteUtil;
import ps.demo.util.MyRestTemplateUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Slf4j
public class QpsControllerTest {


    static File logfile = MyFileUtil.getFileTsInHomeDir("QpsTest");

    public static void main(String[] args) throws Exception {

        /*
        For some reason, invokeAll () will call get () for each future produced.
        Thus, it will wait the tasks to finish and that is why it may throw InterruptedException
        (while submit () not waiting for the completion of all tasks and throws nothing).
         */

        int[] qpsArr = new int [] {100, 500, 1000};
        int requestsPerStage = 10;

        ExecutorService executorService = Executors.newCachedThreadPool();

        int totalRequests = qpsArr.length * requestsPerStage;
        log.info("--->>Total requests will be {}", totalRequests);
        List<Future<QpsBean>> futureList = new ArrayList<>();
        for (int qi = 0, qilen = qpsArr.length; qi < qilen; qi++) {
            int qps = qpsArr[qi];
            log.info("--->>Queries Per Second is {}", qps);
            for (int i = 0; i < requestsPerStage; i++) {
                Callable<QpsBean> callable = new Callable<QpsBean>() {
                    @Override
                    public QpsBean call() throws Exception {
                        MyPfTiming myPfTiming = MyPfTiming.start();
                        QpsBean qpsBean = httpCallServer();
                        qpsBean.setTimeCost(myPfTiming.timeMs());
                        return qpsBean;
                    }
                };
                Future<QpsBean> future = executorService.submit(callable);
                futureList.add(future);
                log.info("------------------>>Going to sleep ms={}", 1000 / qps);
                Thread.sleep(1000 / qps);
            }
        }

        log.info("--->> Send request done, waiting for response...");
        //List<Future<QpsBean>> futureList = executorService.invokeAll(callableList);
        for (Future<QpsBean> future : futureList) {
            QpsBean qpsBean = future.get();
            log.info("--->>"+qpsBean);
            MyReadWriteUtil.writeObjectToFile(logfile, qpsBean);
        }

        log.info("--->> to shut down executor...");
        executorService.shutdown();
        log.info("--->> done!");
    }

    private static QpsBean httpCallServer() {
        String qpsUrl = "http://localhost:8080/api/qps/get";
        ParameterizedTypeReference<QpsBean> responseType = new ParameterizedTypeReference<QpsBean>() {
        };
        ResponseEntity<QpsBean> responseEntity = MyRestTemplateUtil.getInstance().getForObject(qpsUrl, responseType);
        QpsBean qpsBean = responseEntity.getBody();

        log.info("--->>Qps test result={}", qpsBean);
        //ReadWriteUtil.writeToFileInHomeDir(logfile, qpsBean);
        return qpsBean;
    }

    @Getter
    @Setter
    @ToString
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode(callSuper = false)
    public static class QpsBean {
        private String name;
        private int price;
        private long timeCost;
    }

}