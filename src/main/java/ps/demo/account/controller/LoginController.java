


package ps.demo.account.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ps.demo.account.dto.*;
import ps.demo.account.helper.MyErrorUtils;
import ps.demo.account.helper.MyPrincipalUtils;
import ps.demo.account.model.LoginUserDetail;
import ps.demo.account.service.LoginServiceImpl;
import ps.demo.account.service.LoginUserServiceImpl;

import ps.demo.account.service.UserRoleServiceImpl;
import ps.demo.common.MyBaseController;
import ps.demo.common.MyPageResData;
import ps.demo.exception.BadRequestException;
import ps.demo.exception.CodeEnum;
import ps.demo.util.MyBeanUtil;
import ps.demo.util.MyMD5Util;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/login")
public class LoginController extends MyBaseController {

    @Autowired
    private LoginUserServiceImpl loginUserService;

    @Autowired
    private UserRoleServiceImpl userRoleService;

    @Autowired
    private LoginServiceImpl loginServiceImpl;

    @GetMapping("/")
    public ModelAndView navigateToLogin(Model model) {
        model.addAttribute("loginReq", new LoginReq());
        //To login.html page
        return new ModelAndView("login", "loginModel", model);
    }

    @PostMapping("/")
    public ModelAndView login(LoginReq loginReq, Model model) {
        log.info("Login loginReq={}", loginReq);
        model.addAttribute("loginReq", loginReq);
        LoginUserDetail loginUserDetail = loginServiceImpl.findUserByUserName(loginReq.getUsername());
        if (loginUserDetail.getDisabled() != null && loginUserDetail.getDisabled()) {
            MyErrorUtils.setLastError(new BadRequestException(CodeEnum.FORBIDDEN), null);
            return new ModelAndView("login", "loginModel", model);
            //throw new BadRequestException(CodeEnum.FORBIDDEN);
        }
        if (!MyMD5Util.checkPassword(loginReq.getPassword(), loginUserDetail.getPassword())) {
            Integer failedCount = loginUserDetail.getFailedCount();
            if (failedCount == null) {
                failedCount = 0;
            }
            loginUserDetail.setFailedCount(failedCount++);
            if (failedCount > 5) {
                loginUserDetail.setDisabled(true);
            }
            loginUserService.save(loginUserDetail);
            MyErrorUtils.setLastError(new BadRequestException(CodeEnum.UNAUTHORIZED), null);
            return new ModelAndView("login", "loginModel", model);
            //throw new BadRequestException(CodeEnum.UNAUTHORIZED);
        }
        loginUserDetail.setPassword("");
        List<UserRoleDto> userRoleDtoList = userRoleService.findByAttribute("userId", loginUserDetail.getId());
        loginUserDetail.setRoles(userRoleDtoList);
        MyPrincipalUtils.setCurrentUser(loginUserDetail);
        return new ModelAndView("redirect:/api/home/home-tmlf");
    }


    @GetMapping("/logout")
    public ModelAndView getLogout(HttpServletRequest request) {
        MyPrincipalUtils.logoutCurrentUser(request);
        return new ModelAndView("redirect:/api/login/");
    }

    @PostMapping("/logout")
    public ModelAndView postLogout(HttpServletRequest request) {
        MyPrincipalUtils.logoutCurrentUser(request);
        return new ModelAndView("redirect:/api/login/");
    }

}




