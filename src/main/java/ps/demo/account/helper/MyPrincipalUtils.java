package ps.demo.account.helper;


import org.apache.commons.lang3.StringUtils;
import ps.demo.account.dto.UserRoleDto;
import ps.demo.account.model.LoginUserDetail;
import ps.demo.common.SpringContextHolder;
import ps.demo.util.MyRequestContextUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class MyPrincipalUtils {

    public static final String ROLE_USER = "user";
    public static final String ROLE_ADMIN = "admin";

    public static final String USER_DETAIL_PRINCIPAL_KEY = "USER_DETAIL_PRINCIPAL_KEY";

    public static void setCurrentUser(LoginUserDetail loginUserDetail) {
        HttpServletRequest request = MyRequestContextUtil.getRequest();
        if (request == null) {
            return;
        }
        request.getSession().setAttribute(USER_DETAIL_PRINCIPAL_KEY, loginUserDetail);
    }

    public static LoginUserDetail getCurrentUser() {
        HttpServletRequest request = MyRequestContextUtil.getRequest();
        if (request != null) {
            Object object = request.getSession().getAttribute(USER_DETAIL_PRINCIPAL_KEY);
            return (LoginUserDetail) object;
        }
        return null;
    }

    public static void logoutCurrentUser(HttpServletRequest request) {
        if (null == request) {
            request = MyRequestContextUtil.getRequest();
        }
        request.getSession().removeAttribute(USER_DETAIL_PRINCIPAL_KEY);
    }

    public static LoginUserDetail getCurrentUser(HttpServletRequest request) {
        if (request == null) {
            return getCurrentUser();
        }
        Object object = request.getSession().getAttribute(USER_DETAIL_PRINCIPAL_KEY);
        return (LoginUserDetail) object;
    }

    public static Long getCurrentUserId() {
        LoginUserDetail userDetails = getCurrentUser();

        if (userDetails != null) {
            return ((LoginUserDetail)userDetails).getId();
        }
        return -1L;
    }

    public static boolean isAdmin() {
        return hasRoles(ROLE_ADMIN);
    }

    public static boolean hasRoles(String ... roles) {
        LoginUserDetail curUser = getCurrentUser();
        if (curUser == null) {
            return false;
        }
        List<UserRoleDto> userRoleDtoList = curUser.getRoles();
        for (UserRoleDto userRole : userRoleDtoList) {
            if (userRole == null || StringUtils.isBlank(userRole.getRoleName())) {
                continue;
            }
            for (String role : roles) {
                if (StringUtils.isBlank(role)) {
                    continue;
                }
                if (role.equalsIgnoreCase(userRole.getRoleName().trim())) {
                    return true;
                }
            }
        }
        return false;
    }


}
