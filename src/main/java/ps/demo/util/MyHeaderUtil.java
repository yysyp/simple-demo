package ps.demo.util;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

public class MyHeaderUtil {

    public static boolean isTrace() {
        HttpServletRequest httpServletRequest = MyRequestContextUtil.getRequest();
        if (httpServletRequest != null) {
            String trace = httpServletRequest.getHeader("trace");
            return StringUtils.isNotBlank(trace);
        }
        return false;
    }
}
