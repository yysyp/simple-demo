package ps.demo.util;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class MyConvertUtil {

    //For treating excel loaded List<List<String>> table, to make all the lines List<String> has same length.
    public static List<String> alignSize(List<String> line, int length) {
        if (line == null) {
            List defaultLine = new ArrayList<>();
            for (int i = 0; i < length; i++) {
                defaultLine.add("");
            }
            return defaultLine;
        }
        if (line.size() < length) {
            for (int i = line.size(); i < length; i++) {
                line.add("");
            }
        }
        return line;
    }

    public static List<String> convertToStringList(List<Object> objects) {
        List<String> stringList = objects.stream().map(e -> e + "").collect(Collectors.toList());
        return stringList;
    }

    public static <T> List<Object> convertToObjectList(List<T> ts) {
        List<Object> objList = ts.stream().collect(Collectors.toList());
        return objList;
    }

    public static <T> List<T> convertToTList(List<Object> objects) {
        List<T> objList = objects.stream().map(e -> (T) e).collect(Collectors.toList());
        return objList;
    }

    public static <T> List<T> castList(List list, Class<T> clazz) {
        List<T> result = new ArrayList<T>();
        //if (obj instanceof List<?>) {
        for (Object o : (List<?>) list) {
            result.add(clazz.cast(o));
        }
        //}
        return result;
    }

    public static String getAsString(List<String> line, int i) {
        if (line == null || i >= line.size()) {
            return "";
        }
        String var = line.get(i);
        if (var == null) {
            return "";
        }
        return var.toString();
    }

    public static Integer getAsInteger(List<String> line, int i) {
        if (line == null || i >= line.size()) {
            return 0;
        }
        String var = line.get(i);
        if (var == null) {
            return 0;
        }
        try {
            return Integer.parseInt(var.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static Boolean getAsBoolean(List<String> line, int i) {
        if (line == null || i >= line.size()) {
            return false;
        }
        String var = line.get(i);
        if (var == null) {
            return false;
        }
        try {
            return Boolean.parseBoolean(var.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static BigDecimal getAsDecimal(List<String> line, int i) {
        if (line == null || i >= line.size()) {
            return new BigDecimal("0");
        }
        String var = line.get(i);
        if (var == null) {
            return new BigDecimal("0");
        }
        try {
            return new BigDecimal(var.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return new BigDecimal("0");
        }
    }

    public static Date getAsDate(List<String> line, int i, String fmt) {
        if (line == null || i >= line.size()) {
            return null;
        }
        String var = line.get(i);
        if (var == null) {
            return null;
        }
        try {
            return new SimpleDateFormat(fmt).parse(line.get(i));
        } catch (ParseException e) {
            return null;
        }
    }

    public static String decimalToString(BigDecimal decimal) {
        if (decimal == null) {
            return "";
        }
        decimal.setScale(2, BigDecimal.ROUND_HALF_UP);
        return decimal.toString();
    }

}
