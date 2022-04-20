

package ps.demo.account.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;
import ps.demo.common.MyBaseController;
import ps.demo.common.MyPageReq;
import ps.demo.common.MyPageResData;
import ps.demo.account.dto.LoginUserDto;
import ps.demo.account.dto.LoginUserReq;
import ps.demo.account.service.LoginUserServiceImpl;
import ps.demo.util.MyBeanUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import lombok.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.math.*;

@Slf4j
@RestController
@RequestMapping("/api/account/login-user")
public class LoginUserTfController extends MyBaseController {

    @Autowired
    private LoginUserServiceImpl loginUserServiceImpl;

    @GetMapping("/form")
    public ModelAndView createForm(Model model) {
        model.addAttribute("loginUserDto", new LoginUserDto());
        return new ModelAndView("account/login-user-form", "loginUserModel", model);
    }

    @PostMapping("/save")
    public ModelAndView save(LoginUserReq loginUserReq, HttpServletRequest request) {
        LoginUserDto loginUserDto = new LoginUserDto();
        
            
            
            
            
            
            
            
            
            
            
            
            loginUserDto.setDisabled(null != request.getParameter("disabled"));
            
            
            
            
            
            
            
        
        MyBeanUtil.copyProperties(loginUserReq, loginUserDto);
        initBaseCreateModifyTs(loginUserDto);
        LoginUserDto loginUserResult = loginUserServiceImpl.save(loginUserDto);
        return new ModelAndView("redirect:/api/account/login-user");
    }

    @GetMapping("/list")
    public ModelAndView navigateToQuery() {
        return new ModelAndView("redirect:/api/account/login-user");
    }

    @GetMapping
    public ModelAndView query(Model model) {
        LoginUserReq loginUserReq = new LoginUserReq();
        model.addAttribute("loginUserReq", loginUserReq);
        Pageable pageable = constructPagable(loginUserReq);
        Page<LoginUserDto> loginUserDtoPage = loginUserServiceImpl.findInPage(pageable);
        MyPageResData<LoginUserDto> myPageResData = new MyPageResData<>(loginUserDtoPage,
                loginUserReq.getCurrent(), loginUserReq.getSize());
        model.addAttribute("loginUserReq", loginUserReq);
        model.addAttribute("page", myPageResData);
        return new ModelAndView("account/login-user-list", "loginUserModel", model);
    }

    @GetMapping("/{id}")
    public ModelAndView getById(@PathVariable("id") Long id, Model model) {
        LoginUserDto loginUserDto = loginUserServiceImpl.findById(id);
        model.addAttribute("loginUserDto", loginUserDto);
        return new ModelAndView("account/login-user-view", "loginUserModel", model);
    }

    @PostMapping("/list")
    public ModelAndView list(Model model, LoginUserReq loginUserReq) {
        Pageable pageable = constructPagable(loginUserReq);
        LoginUserDto loginUserDto = new LoginUserDto();
        String key = loginUserReq.getKey();
        if (StringUtils.isNotBlank(key)) {
            String percentWrapKey = "%" + key + "%";
            
            
            loginUserDto.setUserName(percentWrapKey);
            
            
            loginUserDto.setPassword(percentWrapKey);
            
            
            loginUserDto.setFirstName(percentWrapKey);
            
            
            loginUserDto.setLastName(percentWrapKey);
            
            
            loginUserDto.setSex(percentWrapKey);
            
            
            loginUserDto.setDepartment(percentWrapKey);
            
            
            loginUserDto.setPhone(percentWrapKey);
            
            
            loginUserDto.setEmail(percentWrapKey);
            
            
            loginUserDto.setCompany(percentWrapKey);
            
            
            loginUserDto.setSalute(percentWrapKey);
            
            
            
            
            
            loginUserDto.setLastLoginIp(percentWrapKey);
            
            
            
            
            loginUserDto.setComments(percentWrapKey);
            
            
        }
        //MyBeanUtil.copyProperties(loginUserReq, loginUserDto);
        Page<LoginUserDto> loginUserDtoPage = loginUserServiceImpl.findByAttributesInPage(loginUserDto, true, pageable);
        MyPageResData<LoginUserDto> myPageResData = new MyPageResData<>(loginUserDtoPage,
                loginUserReq.getCurrent(), loginUserReq.getSize());
        model.addAttribute("loginUserReq", loginUserReq);
        model.addAttribute("page", myPageResData);
        return new ModelAndView("account/login-user-list", "loginUserModel", model);
    }

    @GetMapping("/modify/{id}")
    public ModelAndView modify(@PathVariable("id") Long id, Model model) {
        LoginUserDto loginUserDto = loginUserServiceImpl.findById(id);
        model.addAttribute("loginUserDto", loginUserDto);
        return new ModelAndView("account/login-user-modify", "loginUserModel", model);
    }

    @PostMapping("/modify")
    public ModelAndView saveOrUpdate(LoginUserDto loginUserDto, HttpServletRequest request) {
        initBaseCreateModifyTs(loginUserDto);
        
            
            
            
            
            
            
            
            
            
            
            
            loginUserDto.setDisabled(null != request.getParameter("disabled"));
            
            
            
            
            
            
            
        
        LoginUserDto updatedLoginUserDto = loginUserServiceImpl.save(loginUserDto);
        return new ModelAndView("redirect:/api/account/login-user");
    }

    @GetMapping("/remove/{id}")
    public ModelAndView remove(@PathVariable("id") Long id) {
        loginUserServiceImpl.deleteById(id);
        return new ModelAndView("redirect:/api/account/login-user");
    }

}


