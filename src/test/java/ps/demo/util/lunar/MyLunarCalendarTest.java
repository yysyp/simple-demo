package ps.demo.util.lunar;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class MyLunarCalendarTest {

    public static void main(String[] args) {
        int[] lunar = MyLunarCalendar.solarToLunar(2022, 4, 19);
        System.out.println(lunar[0]+" 年 "+lunar[1]+" 月 "+lunar[2]+" 日 "+lunar[3]+" 润月 ");

        int[] solar = MyLunarCalendar.lunarToSolar(2022, 3, 19, true);

        System.out.println(solar[0]+" Year "+solar[1]+" Month "+solar[2]+" Day");
    }

}