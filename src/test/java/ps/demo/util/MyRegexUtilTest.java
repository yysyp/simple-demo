package ps.demo.util;

import static org.junit.jupiter.api.Assertions.*;

public class MyRegexUtilTest {

    public static void main(String []args) {
        String content = "hello world the timeis[353] good day...";
        String time1 = MyRegexUtil.findByRegex(content, "\\[(.*)\\]", 1);
        System.out.println("time1="+time1);
        String time2 = MyRegexUtil.findByRegex(content, "(\\d+)", 0);
        System.out.println("time2="+time2);
    }

}