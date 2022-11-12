package pslab;

import java.math.BigDecimal;

public class BigDecimalTest {

    public static void main(String[] args) {
        BigDecimal b1 = new BigDecimal("1");
        BigDecimal b2 = new BigDecimal("3");
        BigDecimal r = b1.divide(b2, 6, BigDecimal.ROUND_HALF_UP);
        System.out.println("===>>r="+r);
    }
}
