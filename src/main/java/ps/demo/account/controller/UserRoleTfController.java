



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
import ps.demo.account.dto.UserRoleDto;
import ps.demo.account.dto.UserRoleReq;
import ps.demo.account.service.UserRoleServiceImpl;
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
@RequestMapping("/api/account/user-role")
public class UserRoleTfController extends MyBaseController {

    @Autowired
    private UserRoleServiceImpl userRoleServiceImpl;

    @GetMapping("/form")
    public ModelAndView createForm(Model model) {
        model.addAttribute("userRoleDto", new UserRoleDto());
        return new ModelAndView("account/user-role-form", "userRoleModel", model);
    }

    @PostMapping("/save")
    public ModelAndView save(UserRoleReq userRoleReq, HttpServletRequest request) {
        UserRoleDto userRoleDto = new UserRoleDto();
        
            
            
            
        
        MyBeanUtil.copyProperties(userRoleReq, userRoleDto);
        initBaseCreateModifyTs(userRoleDto);
        UserRoleDto userRoleResult = userRoleServiceImpl.save(userRoleDto);
        return new ModelAndView("redirect:/api/account/user-role");
    }

    @GetMapping("/list")
    public ModelAndView navigateToQuery() {
        return new ModelAndView("redirect:/api/account/user-role");
    }

    @GetMapping
    public ModelAndView query(Model model) {
        UserRoleReq userRoleReq = new UserRoleReq();
        model.addAttribute("userRoleReq", userRoleReq);
        Pageable pageable = constructPagable(userRoleReq);
        Page<UserRoleDto> userRoleDtoPage = userRoleServiceImpl.findInPage(pageable);
        MyPageResData<UserRoleDto> myPageResData = new MyPageResData<>(userRoleDtoPage,
                userRoleReq.getCurrent(), userRoleReq.getSize());
        model.addAttribute("userRoleReq", userRoleReq);
        model.addAttribute("page", myPageResData);
        return new ModelAndView("account/user-role-list", "userRoleModel", model);
    }

    @GetMapping("/{id}")
    public ModelAndView getById(@PathVariable("id") Long id, Model model) {
        UserRoleDto userRoleDto = userRoleServiceImpl.findById(id);
        model.addAttribute("userRoleDto", userRoleDto);
        return new ModelAndView("account/user-role-view", "userRoleModel", model);
    }

    @PostMapping("/list")
    public ModelAndView list(Model model, UserRoleReq userRoleReq) {
        Pageable pageable = constructPagable(userRoleReq);
        UserRoleDto userRoleDto = new UserRoleDto();
        String key = userRoleReq.getKey();
        if (StringUtils.isNotBlank(key)) {
            String percentWrapKey = "%" + key + "%";
            
            
            
            
            userRoleDto.setRoleName(percentWrapKey);
            
            
        }
        //MyBeanUtil.copyProperties(userRoleReq, userRoleDto);
        Page<UserRoleDto> userRoleDtoPage = userRoleServiceImpl.findByAttributesInPage(userRoleDto, true, pageable);
        MyPageResData<UserRoleDto> myPageResData = new MyPageResData<>(userRoleDtoPage,
                userRoleReq.getCurrent(), userRoleReq.getSize());
        model.addAttribute("userRoleReq", userRoleReq);
        model.addAttribute("page", myPageResData);
        return new ModelAndView("account/user-role-list", "userRoleModel", model);
    }

    @GetMapping("/modify/{id}")
    public ModelAndView modify(@PathVariable("id") Long id, Model model) {
        UserRoleDto userRoleDto = userRoleServiceImpl.findById(id);
        model.addAttribute("userRoleDto", userRoleDto);
        return new ModelAndView("account/user-role-modify", "userRoleModel", model);
    }

    @PostMapping("/modify")
    public ModelAndView saveOrUpdate(UserRoleDto userRoleDto, HttpServletRequest request) {
        initBaseCreateModifyTs(userRoleDto);
        
            
            
            
        
        UserRoleDto updatedUserRoleDto = userRoleServiceImpl.save(userRoleDto);
        return new ModelAndView("redirect:/api/account/user-role");
    }

    @GetMapping("/remove/{id}")
    public ModelAndView remove(@PathVariable("id") Long id) {
        userRoleServiceImpl.deleteById(id);
        return new ModelAndView("redirect:/api/account/user-role");
    }

}




