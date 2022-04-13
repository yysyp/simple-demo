package ps.demo.util;

import static org.junit.jupiter.api.Assertions.*;

public class MySimpleLimiterTest {

    public static void main(String[] args) {
        long second = 1000000000;
        long interval = (long) (second * 0.5);
        MySimpleLimiter mySimpleLimiter = new MySimpleLimiter(interval);

        for (int i = 0; i < 100; i++) {
            mySimpleLimiter.acquire();
            System.out.println("-->"+i+": "+MyTimeUtil.getNowStr("hh-mm-ss.SSS"));
        }
    }

}