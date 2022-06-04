package ps.demo.util;

import java.util.Properties;

public class MyStringUtilTest2 {


    public static void main(String[] args) {
        Properties companyAddrProperties = MyReadWriteUtil.readProperties(MyFileUtil.getFileInHomeDir("company-address.properties"));
        Properties newProperties = new Properties();
        for (Object key : companyAddrProperties.keySet()) {
            newProperties.put(MyRegexUtil.regularString(key+""), companyAddrProperties.get(key));
        }
        MyReadWriteUtil.writeProperties(MyFileUtil.getFileTsInHomeDir("newProperties.properties"), newProperties, false);
        //

        String text1 = "ABCBDAB";
        String text2 = "BDCABA";
        int len1 = MyStringUtil.getLongestCommonSequence(text1, text2);
        System.out.println("len1="+len1);



        text1 = " 12asdfa";
        text2 = " we2rasdaswer";
        int len2 = MyStringUtil.getLongestCommonSequence(text1, text2);
        System.out.println("len2="+len2);

        text1 = "";
        text2 = null;
        System.out.println("string lcs ratio="+MyStringUtil.getLongestCommonSequenceRatio(text1, text2));
        System.out.println("string contains ratio="+MyStringUtil.eitherContainsRatio(text1, text2));
        System.out.println("string 3 contains and 7 lcs ratio="+MyStringUtil.getLcsAndContainsRatio(text1, text2));

    }

}