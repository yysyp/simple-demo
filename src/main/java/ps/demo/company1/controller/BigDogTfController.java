



package ps.demo.company1.controller;

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
import ps.demo.company1.dto.BigDogDto;
import ps.demo.company1.dto.BigDogReq;
import ps.demo.company1.service.BigDogServiceImpl;
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
@RequestMapping("/api/company1/big-dog")
public class BigDogTfController extends MyBaseController {

    @Autowired
    private BigDogServiceImpl bigdogserviceimpl;

    @GetMapping("/form")
    public ModelAndView createForm(Model model) {
        model.addAttribute("bigdogdto", new BigDogDto());
        return new ModelAndView("company1/big-dog-form", "bigdogModel", model);
    }

    @PostMapping("/save")
    public ModelAndView save(BigDogReq bigdogreq, HttpServletRequest request) {
        BigDogDto bigdogdto = new BigDogDto();
        
            
            
            
            
            
            bigdogdto.setPassed(null != request.getParameter("passed"));
            
            
            
        
        MyBeanUtil.copyProperties(bigdogreq, bigdogdto);
        initBaseCreateModifyTs(bigdogdto);
        BigDogDto bigdogResult = bigdogserviceimpl.save(bigdogdto);
        return new ModelAndView("redirect:/api/company1/big-dog");
    }

    @GetMapping("/list")
    public ModelAndView navigateToQuery() {
        return new ModelAndView("redirect:/api/company1/big-dog");
    }

    @GetMapping
    public ModelAndView query(Model model) {
        BigDogReq bigdogreq = new BigDogReq();
        model.addAttribute("bigdogreq", bigdogreq);
        Pageable pageable = constructPagable(bigdogreq);
        Page<BigDogDto> bigdogdtoPage = bigdogserviceimpl.findByPage(pageable);
        MyPageResData<BigDogDto> myPageResData = new MyPageResData<>(bigdogdtoPage,
                bigdogreq.getCurrent(), bigdogreq.getSize());
        model.addAttribute("bigdogreq", bigdogreq);
        model.addAttribute("page", myPageResData);
        return new ModelAndView("company1/big-dog-list", "bigdogModel", model);
    }

    @GetMapping("/{id}")
    public ModelAndView getById(@PathVariable("id") Long id, Model model) {
        BigDogDto bigdogdto = bigdogserviceimpl.findById(id);
        model.addAttribute("bigdogdto", bigdogdto);
        return new ModelAndView("company1/big-dog-view", "bigdogModel", model);
    }

    @PostMapping("/list")
    public ModelAndView list(Model model, BigDogReq bigdogreq) {
        Pageable pageable = constructPagable(bigdogreq);
        BigDogDto bigdogdto = new BigDogDto();
        String key = bigdogreq.getKey();
        if (StringUtils.isNotBlank(key)) {
            String percentWrapKey = "%" + key + "%";
            
                
                bigdogdto.setFirstName(percentWrapKey);
                
                
                bigdogdto.setLastName(percentWrapKey);
                
                
                
                
                
                bigdogdto.setComments(percentWrapKey);
                
                
            
        }
        //MyBeanUtil.copyProperties(bigdogreq, bigdogdto);
        Page<BigDogDto> bigdogdtoPage = bigdogserviceimpl.findByPage(bigdogdto, true, pageable);
        MyPageResData<BigDogDto> myPageResData = new MyPageResData<>(bigdogdtoPage,
                bigdogreq.getCurrent(), bigdogreq.getSize());
        model.addAttribute("bigdogreq", bigdogreq);
        model.addAttribute("page", myPageResData);
        return new ModelAndView("company1/big-dog-list", "bigdogModel", model);
    }

    @GetMapping("/modify/{id}")
    public ModelAndView modify(@PathVariable("id") Long id, Model model) {
        BigDogDto bigdogdto = bigdogserviceimpl.findById(id);
        model.addAttribute("bigdogdto", bigdogdto);
        return new ModelAndView("company1/big-dog-modify", "bigdogModel", model);
    }

    @PostMapping("/modify")
    public ModelAndView saveOrUpdate(BigDogDto bigdogdto, HttpServletRequest request) {
        initBaseCreateModifyTs(bigdogdto);
        
            
            
            
            
            
            bigdogdto.setPassed(null != request.getParameter("passed"));
            
            
            
        
        BigDogDto updatedBigDogDto = bigdogserviceimpl.save(bigdogdto);
        return new ModelAndView("redirect:/api/company1/big-dog");
    }

    @GetMapping("/remove/{id}")
    public ModelAndView remove(@PathVariable("id") Long id) {
        bigdogserviceimpl.deleteById(id);
        return new ModelAndView("redirect:/api/company1/big-dog");
    }

}




