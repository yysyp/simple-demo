package pslab;

import org.apache.commons.lang3.StringUtils;

public class StringSplitTest {

    public static void main(String [] args) {
        String s = "S1026-3-1/2&4\\5";
        String s1 = StringUtils.substringBefore(s, "-");
        System.out.println(s1);
        String s2 = StringUtils.substringAfter(s, "-");
        System.out.println(s2);
        String s3 = s2.replaceAll("-", "")
                .replaceAll("/", "")
                .replaceAll("&", "")
                .replaceAll("\\\\", "");
        System.out.println(s3);
    }
}
