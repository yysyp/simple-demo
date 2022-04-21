package ps.demo.account.helper;

import ps.demo.account.model.LoginUserDetail;
import ps.demo.util.MyRequestContextUtil;

import javax.servlet.http.HttpServletRequest;

public class MyErrorUtils {

    public static final String LAST_ERROR_KEY = "LAST_ERROR_KEY";

    public static Exception getLastError(HttpServletRequest request) {
        if (request == null) {
            request = MyRequestContextUtil.getRequest();
        }
        if (request != null) {
            Object object = request.getSession().getAttribute(LAST_ERROR_KEY);
            return (Exception) object;
        }
        return null;
    }

    public static void setLastError(Exception e, HttpServletRequest request) {
        if (request == null) {
            request = MyRequestContextUtil.getRequest();
        }
        if (request != null) {
            request.getSession().setAttribute(LAST_ERROR_KEY, e);
        }
    }

}
