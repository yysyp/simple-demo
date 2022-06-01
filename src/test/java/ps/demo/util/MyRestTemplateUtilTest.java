package ps.demo.util;

import static org.junit.jupiter.api.Assertions.*;

public class MyRestTemplateUtilTest {


    public static void main(String[] args) {
        String key = "小米集团";
        String response = MyRestTemplateUtil.getInstance()
                .getForString("http://www.baidu.com/s?wd={kw}", key);
        //response.
        System.out.println(response);
    }
}