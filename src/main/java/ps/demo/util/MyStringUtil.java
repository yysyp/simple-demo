package ps.demo.util;

import org.apache.commons.lang3.StringUtils;

public class MyStringUtil {
    public static final char UNDERLINE = '_';

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

    public static String toDbName(String javaName) {
        if (StringUtils.isBlank(javaName)) {
            return javaName;
        }
        int len = javaName.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = javaName.charAt(i);
            if (Character.isUpperCase(c)) {
                sb.append(UNDERLINE);
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
        return StringUtils.lowerCase(""+s.charAt(0)).concat(s.substring(1));
    }

    public static String lowerCaseFirstChar(String s) {
        if (Character.isLowerCase(s.charAt(0))){
            return s;
        } else {
            return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
        }
    }

}
