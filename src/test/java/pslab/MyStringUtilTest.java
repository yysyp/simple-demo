package pslab;

import ps.demo.util.MyStringUtil;

public class MyStringUtilTest {

    public static void main(String[] args) {
        String q = "负债和所有者权益或股东权益合计元";
        System.out.println("1>>"+MyStringUtil.getLcsOrMixContainsRatio(q, "所有者权益或股东权益合计"));
        System.out.println("2>>"+MyStringUtil.getLcsOrMixContainsRatio(q, "负债和所有者权益或股东权益总计"));
        System.out.println("3>>"+MyStringUtil.getLcsOrMixContainsRatio(q, "负债和所有者权益或股东权益合计"));


        System.out.println("==31:"+MyStringUtil.getLcsOrMixContainsRatio("二、营业总成本(元)", "营业成本"));
        System.out.println("==32:"+MyStringUtil.getLcsOrMixContainsRatio("其中：营业成本(元)", "营业成本"));
    }
}
