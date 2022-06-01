package ps.demo.util;

import com.github.houbb.opencc4j.util.ZhConverterUtil;

import java.util.Properties;

public class MyStringUtilTest2 {


    public static void main(String[] args) {
        String str = "中华人民";
        String target = "人民共和dtrtyr";

        double score1 = MyStringUtil.getLevenshteinDistanceRatio(str, target);
        double score2 = MyStringUtil.getSimilarityWith(str, target);
        System.out.println("score1="+score1 + "\t score2="+score2);

        System.out.println(ZhConverterUtil.toSimple("上海鴻置"));
        String tests3 = "上海鴻置";
        tests3 = ZhConverterUtil.toSimple(tests3).replaceAll("有限公司", "")
                .replaceAll("科技", "").replaceAll("大学", "")
                .replaceAll("集团", "").replaceAll("课题组", "")
                .replaceAll("机构", "").replaceAll("学院", "");

        System.out.println("tests3="+tests3);

        Properties companyAddrProperties = MyReadWriteUtil.readProperties(MyFileUtil.getFileInHomeDir("company-address.properties"));
        Properties newProperties = new Properties();
        for (Object key : companyAddrProperties.keySet()) {
            newProperties.put(MyRegexUtil.regularString(key+""), companyAddrProperties.get(key));
        }
        MyReadWriteUtil.writeProperties(MyFileUtil.getFileTsInHomeDir("newProperties.properties"), newProperties, false);
    }

}