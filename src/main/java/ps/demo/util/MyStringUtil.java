package ps.demo.util;

import com.github.houbb.opencc4j.util.ZhConverterUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

    public static boolean eitherContains(String x, String y) {
        if (StringUtils.isBlank(x) || StringUtils.isBlank(y)) {
            return false;
        }
        x = MyRegexUtil.regularString(x);
        y = MyRegexUtil.regularString(y);
        return _eitherContains(x, y);
    }

    public static double eitherContainsRatio(String x, String y) {
        x = MyRegexUtil.regularString(x);
        y = MyRegexUtil.regularString(y);
        if (StringUtils.isBlank(x) || StringUtils.isBlank(y)) {
            return 0;
        }
        if (!_eitherContains(x, y)) {
            return 0;
        }
        double m = x.length(), n = y.length();
        return (m < n) ? m / n : n / m;
    }

    private static boolean _eitherContains(String x, String y) {
        if (StringUtils.isBlank(x) || StringUtils.isBlank(y)) {
            return false;
        }
        return x.contains(y) || y.contains(x);
    }

//    public static double getSimilarityWith(String x, String y) {
//        if (StringUtils.isBlank(x) || StringUtils.isBlank(y)) {
//            return 0.0d;
//        }
//
//        double r1 = _similarWith(x, y);
//        double r2 = _similarWith(y, x);
//        return new BigDecimal((r1 + r2) / 2)
//                .setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
//    }
//
//    private static double _similarWith(String s1, String s2) {
//        int f = 0;
//        int m = 0;
//        for (int i = 0, in = s1.length(); i < in; i++) {
//            char c = s1.charAt(i);
//            boolean found = false;
//            for (int j = 0, jn = s2.length(); j < jn; j++) {
//                if (c == s2.charAt(j)) {
//                    found = true;
//                    break;
//                }
//            }
//            if (found) {
//                f++;
//            } else {
//                m++;
//            }
//        }
//        return (double) f / (double) (f + m);
//    }

    public static int getLevenshteinDistance(String x, String y) {
        int m = x.length();
        int n = y.length();

        int[][] T = new int[m + 1][n + 1];
        for (int i = 1; i <= m; i++) {
            T[i][0] = i;
        }
        for (int j = 1; j <= n; j++) {
            T[0][j] = j;
        }

        int cost;
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                cost = x.charAt(i - 1) == y.charAt(j - 1) ? 0 : 1;
                T[i][j] = Integer.min(Integer.min(T[i - 1][j] + 1, T[i][j - 1] + 1),
                        T[i - 1][j - 1] + cost);
            }
        }

        return T[m][n];
    }

    public static double getLevenshteinDistanceRatio(String x, String y) {
        if (x == null || y == null) {
            return 0;
        }
        x = MyRegexUtil.regularString(x);
        y = MyRegexUtil.regularString(y);

        double maxLength = Double.max(x.length(), y.length());
        if (maxLength > 0) {
            return (maxLength - getLevenshteinDistance(x, y)) / maxLength;
            //new BigDecimal(d).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        }
        return 1.0;
    }

    public static int getLongestCommonSequence(String x, String y) {

        int m = x.length(), n = y.length();

        int[][] dp = new int[m + 1][n + 1];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                dp[i][j] = 0;
            }
        }

        for (int i = 1; i <= m; ++i) {
            for (int j = 1; j <= n; ++j) {
                if (x.charAt(i - 1) == y.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i][j - 1], dp[i - 1][j]);
                }
            }
        }
        return dp[m][n];
    }

    public static double getLongestCommonSequenceRatio(String x, String y) {
        if (x == null || y == null) {
            return 0;
        }
        x = MyRegexUtil.regularString(x);
        y = MyRegexUtil.regularString(y);
        if (StringUtils.isBlank(x) || StringUtils.isBlank(y)) {
            return 1.0;
        }

        double averageLength = (x.length() + y.length()) / 2.0;
        return getLongestCommonSequence(x, y) / averageLength;
        //new BigDecimal(d).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static double getLcsAndContainsRatio(String x, String y) {
        if (x == null || y == null) {
            return 0;
        }
        x = MyRegexUtil.regularString(x);
        y = MyRegexUtil.regularString(y);
        if (StringUtils.isBlank(x) && StringUtils.isBlank(y)) {
            return 1.0;
        }
        if (StringUtils.isBlank(x) || StringUtils.isBlank(y)) {
            return 0;
        }
        double containsRatio = eitherContainsRatio(x, y);
        double lcsRatio = getLongestCommonSequenceRatio(x, y);
        return containsRatio * 0.5 + lcsRatio * 0.5;
    }

    /**
     * Recommend to use this method for similar string comparison
     * @param x
     * @param y
     * @return
     */
    public static double getLcsOrMixContainsRatio(String x, String y) {
        if (x == null || y == null) {
            return 0;
        }
        x = MyRegexUtil.regularString(x);
        y = MyRegexUtil.regularString(y);
        if (StringUtils.isBlank(x) && StringUtils.isBlank(y)) {
            return 1.0;
        }
        if (StringUtils.isBlank(x) || StringUtils.isBlank(y)) {
            return 0;
        }

        int len1 = x.length();
        int len2 = y.length();
        int minlen = Math.min(len1, len2);
        BigDecimal containRatio = new BigDecimal("0.5").subtract(
                new BigDecimal(minlen).divide(new BigDecimal(len1+len2),
                BigDecimal.ROUND_HALF_UP, 6));

        double containsRatio = eitherContainsRatio(x, y);
        double lcsRatio = getLongestCommonSequenceRatio(x, y);
        return containsRatio * containRatio.doubleValue() + lcsRatio * (1-containRatio.doubleValue());
    }

//    public static int getFuzzyDistance(CharSequence term, CharSequence query, Locale locale) {
//        if (term != null && query != null) {
//            if (locale == null) {
//                throw new IllegalArgumentException("Locale must not be null");
//            } else {
//                String termLowerCase = term.toString().toLowerCase(locale);
//                String queryLowerCase = query.toString().toLowerCase(locale);
//                int score = 0;
//                int termIndex = 0;
//                int previousMatchingCharacterIndex = -2147483648;
//
//                for (int queryIndex = 0; queryIndex < queryLowerCase.length(); ++queryIndex) {
//                    char queryChar = queryLowerCase.charAt(queryIndex);
//
//                    for (boolean termCharacterMatchFound = false; termIndex < termLowerCase.length() && !termCharacterMatchFound; ++termIndex) {
//                        char termChar = termLowerCase.charAt(termIndex);
//                        if (queryChar == termChar) {
//                            ++score;
//                            if (previousMatchingCharacterIndex + 1 == termIndex) {
//                                score += 2;
//                            }
//
//                            previousMatchingCharacterIndex = termIndex;
//                            termCharacterMatchFound = true;
//                        }
//                    }
//                }
//
//                return score;
//            }
//        } else {
//            throw new IllegalArgumentException("Strings must not be null");
//        }
//    }

    public static Date tryStrToDate(String dateStr, String... fmts) {
        if (fmts == null || fmts.length == 0) {

            fmts = new String[]{"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "yyyy-MM-dd'T'HH:mm:ss.SSS", "yyyy-MM-dd'T'HH:mm:ss", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd", "yyyy-MM", "HH:mm:ss", "HH:mm"};
        }
        for (String fmt : fmts) {
            try {
                return new SimpleDateFormat(fmt).parse(dateStr);
            } catch (ParseException e) {
            }
        }
        return null;
    }

}
