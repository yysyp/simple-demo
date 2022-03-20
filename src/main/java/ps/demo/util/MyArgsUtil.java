package ps.demo.util;

import java.util.LinkedHashMap;
import java.util.Map;

public class MyArgsUtil {

    public static Map<String, String> assemberArgs(String[] args) {
        Map<String, String> argsMap = new LinkedHashMap<String, String>();

        if (args != null) {
            for (int i = 0, n = args.length; i < n; i += 2) {
                String key = (args[i] + "").trim().toLowerCase();
                if (i + 1 < args.length) {
                    String value = (args[i + 1] + "").trim();
                    argsMap.put(key, value);
                } else {
                    argsMap.put(key, "");
                }
            }
        }
        return argsMap;
    }

}
