



package ps.demo.company.controller;

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
import ps.demo.company.dto.AbcStaffDto;
import ps.demo.company.dto.AbcStaffReq;
import ps.demo.company.service.AbcStaffServiceImpl;
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
@RequestMapping("/api/company/abc-staff")
public class AbcStaffTfController extends MyBaseController {

    @Autowired
    private AbcStaffServiceImpl abcStaffServiceImpl;

    @GetMapping("/form")
    public ModelAndView createForm(Model model) {
        model.addAttribute("abcStaffDto", new AbcStaffDto());
        return new ModelAndView("company/abc-staff-form", "abcStaffModel", model);
    }

    @PostMapping("/save")
    public ModelAndView save(AbcStaffReq abcStaffReq, HttpServletRequest request) {
        AbcStaffDto abcStaffDto = new AbcStaffDto();
        
            
            
            
            
            
            abcStaffDto.setPassed(null != request.getParameter("passed"));
            
            
            
        
        MyBeanUtil.copyProperties(abcStaffReq, abcStaffDto);
        initBaseCreateModifyTs(abcStaffDto);
        AbcStaffDto abcStaffResult = abcStaffServiceImpl.save(abcStaffDto);
        return new ModelAndView("redirect:/api/company/abc-staff");
    }

    @GetMapping("/list")
    public ModelAndView navigateToQuery() {
        return new ModelAndView("redirect:/api/company/abc-staff");
    }

    @GetMapping
    public ModelAndView query(Model model) {
        AbcStaffReq abcStaffReq = new AbcStaffReq();
        model.addAttribute("abcStaffReq", abcStaffReq);
        Pageable pageable = constructPagable(abcStaffReq);
        Page<AbcStaffDto> abcStaffDtoPage = abcStaffServiceImpl.findByPage(pageable);
        MyPageResData<AbcStaffDto> myPageResData = new MyPageResData<>(abcStaffDtoPage,
                abcStaffReq.getCurrent(), abcStaffReq.getSize());
        model.addAttribute("abcStaffReq", abcStaffReq);
        model.addAttribute("page", myPageResData);
        return new ModelAndView("company/abc-staff-list", "abcStaffModel", model);
    }

    @GetMapping("/{id}")
    public ModelAndView getById(@PathVariable("id") Long id, Model model) {
        AbcStaffDto abcStaffDto = abcStaffServiceImpl.findById(id);
        model.addAttribute("abcStaffDto", abcStaffDto);
        return new ModelAndView("company/abc-staff-view", "abcStaffModel", model);
    }

    @PostMapping("/list")
    public ModelAndView list(Model model, AbcStaffReq abcStaffReq) {
        Pageable pageable = constructPagable(abcStaffReq);
        AbcStaffDto abcStaffDto = new AbcStaffDto();
        String key = abcStaffReq.getKey();
        if (StringUtils.isNotBlank(key)) {
            String percentWrapKey = "%" + key + "%";
            
                
                abcStaffDto.setFirstName(percentWrapKey);
                
                
                abcStaffDto.setLastName(percentWrapKey);
                
                
                
                
                
                abcStaffDto.setComments(percentWrapKey);
                
                
            
        }
        //MyBeanUtil.copyProperties(abcStaffReq, abcStaffDto);
        Page<AbcStaffDto> abcStaffDtoPage = abcStaffServiceImpl.findByPage(abcStaffDto, true, pageable);
        MyPageResData<AbcStaffDto> myPageResData = new MyPageResData<>(abcStaffDtoPage,
                abcStaffReq.getCurrent(), abcStaffReq.getSize());
        model.addAttribute("abcStaffReq", abcStaffReq);
        model.addAttribute("page", myPageResData);
        return new ModelAndView("company/abc-staff-list", "abcStaffModel", model);
    }

    @GetMapping("/modify/{id}")
    public ModelAndView modify(@PathVariable("id") Long id, Model model) {
        AbcStaffDto abcStaffDto = abcStaffServiceImpl.findById(id);
        model.addAttribute("abcStaffDto", abcStaffDto);
        return new ModelAndView("company/abc-staff-modify", "abcStaffModel", model);
    }

    @PostMapping("/modify")
    public ModelAndView saveOrUpdate(AbcStaffDto abcStaffDto, HttpServletRequest request) {
        initBaseCreateModifyTs(abcStaffDto);
        
            
            
            
            
            
            abcStaffDto.setPassed(null != request.getParameter("passed"));
            
            
            
        
        AbcStaffDto updatedAbcStaffDto = abcStaffServiceImpl.save(abcStaffDto);
        return new ModelAndView("redirect:/api/company/abc-staff");
    }

    @GetMapping("/remove/{id}")
    public ModelAndView remove(@PathVariable("id") Long id) {
        abcStaffServiceImpl.deleteById(id);
        return new ModelAndView("redirect:/api/company/abc-staff");
    }

}




