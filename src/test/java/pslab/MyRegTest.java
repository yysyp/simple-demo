package pslab;

import ps.demo.util.MyRegexUtil;

public class MyRegTest {

    public static void main(String[] args) {
        String s = MyRegexUtil.removeChinese("啊冷空气入侵了退货S402-1_2 &ew分合同14\n");
        s = MyRegexUtil.removeSymbolsExceptAlpNumberHyphenUnderline(s);
        System.out.println("s=["+s+"]");
    }
}
