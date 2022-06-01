package ps.demo.util;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.Locale;

public class MyStringUtil {
    public static final char UNDERLINE = '_';
    public static final char HYPHEN = '-';


    public static String randomAscii(int bytes) {
        return RandomStringUtils.randomAscii(bytes);
    }

    public static String randomAlphabetic(int bytes) {
        return RandomStringUtils.randomAlphabetic(bytes);
    }


    public static String toJavaName(String dbName) {
        if (StringUtils.isBlank(dbName)) {
            return dbName;
        }
        int len = dbName.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = dbName.charAt(i);
            if (c == UNDERLINE) {
                if (++i < len) {
                    sb.append(Character.toUpperCase(dbName.charAt(i)));
                }
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    public static String toDbName(String javaAttr) {
        return camelCaseToNewChar(javaAttr, UNDERLINE);
    }

    public static String toUriName(String javaAttr) {
        return camelCaseToNewChar(javaAttr, HYPHEN);
    }

    public static String camelCaseToNewChar(String camelVariable, char ch) {
        if (StringUtils.isBlank(camelVariable)) {
            return camelVariable;
        }
        String uncCamelVriable = StringUtils.uncapitalize(camelVariable);
        int len = uncCamelVriable.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = uncCamelVriable.charAt(i);
            if (Character.isUpperCase(c)) {
                sb.append(ch);
                sb.append(Character.toLowerCase(c));
            } else {
                sb.append(c);
            }
        }

        return sb.toString();
    }

    public static String getLowerCaseBeanName(Class klass) {
        if (klass == null) {
            return null;
        }
        String s = klass.getName();
        if (StringUtils.isEmpty(s)) {
            return s;
        }
        return StringUtils.lowerCase("" + s.charAt(0)).concat(s.substring(1));
    }

    public static String lowerCaseFirstChar(String s) {
        if (Character.isLowerCase(s.charAt(0))) {
            return s;
        } else {
            return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
        }
    }

    public static double getSimilarityRatio2(String proName1, String proName2) {
        String s1 = ("" + proName1).replaceAll("\\s*", "").toLowerCase();
        String s2 = ("" + proName2).replaceAll("\\s*", "").toLowerCase();
        double score = getSimilarityRatio(s1, s2);
        if (score < 0.9d && (s1.contains(s2) || s2.contains(s1))) {
            return 0.9d;
        }
        return score;
    }

    public static double getSimilarityRatio(String proName1, String proName2) {
        int Length1 = proName1.length();
        int Length2 = proName2.length();

        int Distance = 0;
        if (Length1 == 0) {
            Distance = Length2;
        }
        if (Length2 == 0) {
            Distance = Length1;
        }
        if (Length1 != 0 && Length2 != 0) {
            int[][] Distance_Matrix = new int[Length1 + 1][Length2 + 1];
            int Bianhao = 0;
            for (int i = 0; i <= Length1; i++) {
                Distance_Matrix[i][0] = Bianhao;
                Bianhao++;
            }
            Bianhao = 0;
            for (int i = 0; i <= Length2; i++) {
                Distance_Matrix[0][i] = Bianhao;
                Bianhao++;
            }
            char[] Str_1_CharArray = proName1.toCharArray();
            char[] Str_2_CharArray = proName2.toCharArray();
            for (int i = 1; i <= Length1; i++) {
                for (int j = 1; j <= Length2; j++) {
                    if (Str_1_CharArray[i - 1] == Str_2_CharArray[j - 1]) {
                        Distance = 0;
                    } else {
                        Distance = 1;
                    }
                    int Temp1 = Distance_Matrix[i - 1][j] + 1;
                    int Temp2 = Distance_Matrix[i][j - 1] + 1;
                    int Temp3 = Distance_Matrix[i - 1][j - 1] + Distance;
                    Distance_Matrix[i][j] = Temp1 > Temp2 ? Temp2 : Temp1;
                    Distance_Matrix[i][j] = Distance_Matrix[i][j] > Temp3 ? Temp3 : Distance_Matrix[i][j];
                }
            }
            Distance = Distance_Matrix[Length1][Length2];
        }
        double Aerfas = 1 - 1.0 * Distance / (Length1 > Length2 ? Length1 : Length2);
        BigDecimal b = new BigDecimal(Aerfas);
        double Aerfa = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        return Aerfa;
    }

}
