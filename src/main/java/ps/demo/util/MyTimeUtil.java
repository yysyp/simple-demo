package ps.demo.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MyTimeUtil {

    public static String getNowStr() {
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HHmmss");
        return dateTime.format(formatter);
    }

//    public static String getTimestamp() {
//        LocalDateTime localDateTime = LocalDateTime.now();
//        return localDateTime.toString();
//    }
//    public static String getNow() {
//        java.time.format.DateTimeFormatter dateTimeFormatter = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd_HHmmss");
//        return java.time.LocalDateTime.now().format(dateTimeFormatter);
//    }

}
