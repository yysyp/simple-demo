package ps.demo.util;

import org.apache.commons.lang3.StringUtils;

public class MyHeaderUtil {

    public static boolean isTrace() {
        String trace = MyRequestContextUtil.getRequest().getHeader("trace");
        return StringUtils.isNotBlank(trace);
    }
}
