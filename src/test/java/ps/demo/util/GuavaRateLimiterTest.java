package ps.demo.util;

import com.google.common.util.concurrent.RateLimiter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

public class GuavaRateLimiterTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        RateLimiter limiter = RateLimiter.create(10); //QPS
        for (int i = 0; i < 101; i++) {
            performOperation(limiter);

            System.out.println("No: ["+i+"] at: ["+MyTimeUtil.getNowStr(MyTimeUtil.DATE_FORMAT_STR_DATETTIMESSS) + "] : Beep");

        }

        List<Future<Object>> futureList = new ArrayList<>();
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < 100; i++) {
            Future<Object> future = executorService.submit(new Callable<Object>() {
                @Override
                public Object call() throws Exception {
                    limiter.acquire();
                    System.out.println("--->>Fire! at:" + MyTimeUtil.getNowStr(MyTimeUtil.DATE_FORMAT_STR_DATETTIMESSS));
                    return "OK";
                }
            });
            futureList.add(future);
        }

//        for (Future<Object> future : futureList) {
//            System.out.println(future.get());
//        }

    }

    private static void performOperation(RateLimiter limiter) {
        limiter.acquire();
    }

}
