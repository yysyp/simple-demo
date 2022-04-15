package ps.demo.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MyTimeUtil {

    //public final static String DATE_FORMAT_STR_ISO8601 = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
    //public static final String DATE_FORMAT_STR_DATETTIMESSSZ = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    public static final String DATE_FORMAT_STR_DATETTIMESSS = "yyyy-MM-dd'T'HH:mm:ss.SSS";
    public static final String DATE_FORMAT_STR_DATETTIME = "yyyy-MM-dd'T'HH:mm:ss";
    public final static String DATE_FORMAT_STR_PLAIN_DATE = "yyyy-MM-dd";

    public static String getNowStr() {
        return getNowStr("yyyy-MM-dd_HHmmss");
    }

    public static String getNowStryMdTHmsS() {
        return getNowStr(DATE_FORMAT_STR_DATETTIMESSS);
    }

    public String getNowStryMdTHms() {
        return getNowStr(DATE_FORMAT_STR_DATETTIME);
    }

    public String getNowStryMd() {
        return getNowStr(DATE_FORMAT_STR_PLAIN_DATE);
    }

    public static String getNowStr(String pattern) {
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
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
