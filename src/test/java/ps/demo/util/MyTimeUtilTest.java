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

}