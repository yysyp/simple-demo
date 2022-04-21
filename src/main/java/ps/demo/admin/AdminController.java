package ps.demo.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ps.demo.account.dto.LoginUserDto;
import ps.demo.account.dto.LoginUserReq;
import ps.demo.account.helper.MyErrorUtils;
import ps.demo.account.service.LoginUserServiceImpl;
import ps.demo.annotation.MyPermission;
import ps.demo.common.MyBaseController;
import ps.demo.exception.BadRequestException;
import ps.demo.exception.CodeEnum;
import ps.demo.util.MyBeanUtil;
import ps.demo.util.MyMD5Util;
import ps.demo.util.MyTimeUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Tag(name = "This is an admin controller")
@Slf4j
@Controller
@RequestMapping("/api/admin/login-user")
public class AdminController extends MyBaseController {

    @Autowired
    private LoginUserServiceImpl loginUserServiceImpl;

    @MyPermission(roles="admin")
    @GetMapping("/form")
    public ModelAndView createForm(Model model) {
        model.addAttribute("loginUserDto", new LoginUserDto());
        return new ModelAndView("admin/login-user-form", "loginUserModel", model);
    }

    @MyPermission(roles="admin")
    @PostMapping("/save")
    public ModelAndView save(Model model, LoginUserReq loginUserReq, HttpServletRequest request) {
        LoginUserDto loginUserDto = new LoginUserDto();
        loginUserDto.setDisabled(null != request.getParameter("disabled"));
        MyBeanUtil.copyProperties(loginUserReq, loginUserDto);
        List<LoginUserDto> checkExisting = loginUserServiceImpl.findByAttribute("userName", loginUserDto.getUserName());
        if (CollectionUtils.isNotEmpty(checkExisting)) {
            MyErrorUtils.setLastError(new BadRequestException(CodeEnum.DUPLICATED_KEY), null);
            model.addAttribute("loginUserDto", loginUserDto);
            return new ModelAndView("admin/login-user-form", "loginUserModel", model);
        }
        initBaseCreateModifyTs(loginUserDto);
        String md5Pass = MyMD5Util.getMD5(loginUserDto.getPassword());
        loginUserDto.setPassword(md5Pass);
        LoginUserDto loginUserResult = loginUserServiceImpl.save(loginUserDto);
        return new ModelAndView("redirect:/api/account/login-user");
    }

}
