package ps.demo.util;

import static org.junit.jupiter.api.Assertions.*;

public class MyRegexUtilTest {

    public static void main(String []args) {
        String content = "hello world the timeis[353] good day...";
        String time1 = MyRegexUtil.findByRegex(content, "\\[(.*)\\]", 1);
        System.out.println("time1="+time1);
        String time2 = MyRegexUtil.findByRegex(content, "(\\d+)", 0);
        System.out.println("time2="+time2);

        /**
         * /pP ：中的小写 p 是 property 的意思，表示 Unicode 属性，用于 Unicode 正表达式的前缀。
         *
         * P：标点字符
         * L：字母；
         * M：标记符号（一般不会单独出现）；
         * Z：分隔符（比如空格、换行等）；
         * S：符号（比如数学符号、货币符号等）；
         * N：数字（比如阿拉伯数字、罗马数字等）；
         * C：其他字符
         * ————————————————
         */
        String s2 = " 你是? ？@#￥@2~！@#￥%……&*（）——+“|？》《kkhqwermom ~!@+_)(*&^%$#@!~{{}|?><   gog o!\nyes中文就对了——————end ";
        String s2r = s2.replaceAll("[\\pP\\pS\\pZ\\pM\\n]", "");
        System.out.println("s2r==["+ s2r+"]");
    }

}