package ps.demo.util;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.temporal.TemporalField;
import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class MyTimeUtilTest {

    @Test
    void toDate() {

        Date todayDate = MyTimeUtil.toDate(MyTimeUtil.getNowStr("yyyyMMdd"), "yyyyMMdd");
        System.out.println(todayDate);
        LocalDate now = LocalDate.now();
        int year = now.getYear();
        int month = now.getMonthValue();
        int day = now.getDayOfMonth();


        System.out.println(year + "-" + month + "-" + day);

        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        int y = c.get(Calendar.YEAR);
        int m = c.get(Calendar.MONTH);
        int d = c.get(Calendar.DAY_OF_MONTH);

        System.out.println(y + "--"+m+"--"+d );

        System.out.println(MyTimeUtil.getNowStr("yyyy"));
        //System.out.println(MyTimeUtil.toDate("0912", "MMdd"));

        System.out.println(MyTimeUtil.toDate(MyTimeUtil.getNowStr("yyyy")+"0312123456", "yyyyMMddHHmmss"));

        Date date1 = new Date();
        Date date2 = MyTimeUtil.toDate("2021-03-01 12:32:11", "yyyy-MM-dd HH:mm:ss");

        System.out.println(MyTimeUtil.subtractDays(date1, date2));
    }

    @Test
    void toDate2() {
        Date date1 = MyTimeUtil.toDate("12:34", "HH:mm");
        System.out.println("--->>date1="+date1);
        //--->>date1=Thu Jan 01 12:34:00 CST 1970
        String nowStr = MyTimeUtil.getNowStr("HH:mm");
        System.out.println("--->>nowStr"+nowStr);


    }

    @Test
    void addDays() {
        Date date = MyTimeUtil.addDays(new Date(), 5);
        System.out.println("--->date="+date);

        Date date2 = MyTimeUtil.addDays(new Date(), -5);
        System.out.println("--->date2="+date2);

        Date date3 = MyTimeUtil.toDate("2022-12-31", "yyyy-MM-dd");
        System.out.println("--->>date3="+MyTimeUtil.addDays(date3, 1));

        Date date4 = MyTimeUtil.toDate("2022-02-28", "yyyy-MM-dd");
        System.out.println("--->>date4="+MyTimeUtil.addDays(date4, 1));

    }

}