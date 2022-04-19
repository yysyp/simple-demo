



package ps.demo.comp2.controller;

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
import ps.demo.comp2.dto.SmallCatMiaoDto;
import ps.demo.comp2.dto.SmallCatMiaoReq;
import ps.demo.comp2.service.SmallCatMiaoServiceImpl;
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
@RequestMapping("/api/comp2/small-cat-miao")
public class SmallCatMiaoTfController extends MyBaseController {

    @Autowired
    private SmallCatMiaoServiceImpl smallcatmiaoserviceimpl;

    @GetMapping("/form")
    public ModelAndView createForm(Model model) {
        model.addAttribute("smallcatmiaodto", new SmallCatMiaoDto());
        return new ModelAndView("comp2/small-cat-miao-form", "smallcatmiaoModel", model);
    }

    @PostMapping("/save")
    public ModelAndView save(SmallCatMiaoReq smallcatmiaoreq, HttpServletRequest request) {
        SmallCatMiaoDto smallcatmiaodto = new SmallCatMiaoDto();
        
            
            
            
            
            
            
            
            
            
            smallcatmiaodto.setPassed(null != request.getParameter("passed"));
            
            
            
            smallcatmiaodto.setPassed2(null != request.getParameter("passed2"));
            
            
        
        MyBeanUtil.copyProperties(smallcatmiaoreq, smallcatmiaodto);
        initBaseCreateModifyTs(smallcatmiaodto);
        SmallCatMiaoDto smallcatmiaoResult = smallcatmiaoserviceimpl.save(smallcatmiaodto);
        return new ModelAndView("redirect:/api/comp2/small-cat-miao");
    }

    @GetMapping("/list")
    public ModelAndView navigateToQuery() {
        return new ModelAndView("redirect:/api/comp2/small-cat-miao");
    }

    @GetMapping
    public ModelAndView query(Model model) {
        SmallCatMiaoReq smallcatmiaoreq = new SmallCatMiaoReq();
        model.addAttribute("smallcatmiaoreq", smallcatmiaoreq);
        Pageable pageable = constructPagable(smallcatmiaoreq);
        Page<SmallCatMiaoDto> smallcatmiaodtoPage = smallcatmiaoserviceimpl.findByPage(pageable);
        MyPageResData<SmallCatMiaoDto> myPageResData = new MyPageResData<>(smallcatmiaodtoPage,
                smallcatmiaoreq.getCurrent(), smallcatmiaoreq.getSize());
        model.addAttribute("smallcatmiaoreq", smallcatmiaoreq);
        model.addAttribute("page", myPageResData);
        return new ModelAndView("comp2/small-cat-miao-list", "smallcatmiaoModel", model);
    }

    @GetMapping("/{id}")
    public ModelAndView getById(@PathVariable("id") Long id, Model model) {
        SmallCatMiaoDto smallcatmiaodto = smallcatmiaoserviceimpl.findById(id);
        model.addAttribute("smallcatmiaodto", smallcatmiaodto);
        return new ModelAndView("comp2/small-cat-miao-view", "smallcatmiaoModel", model);
    }

    @PostMapping("/list")
    public ModelAndView list(Model model, SmallCatMiaoReq smallcatmiaoreq) {
        Pageable pageable = constructPagable(smallcatmiaoreq);
        SmallCatMiaoDto smallcatmiaodto = new SmallCatMiaoDto();
        String key = smallcatmiaoreq.getKey();
        if (StringUtils.isNotBlank(key)) {
            String percentWrapKey = "%" + key + "%";
            
                
                smallcatmiaodto.setFirstName(percentWrapKey);
                
                
                smallcatmiaodto.setLastName(percentWrapKey);
                
                
                smallcatmiaodto.setLastName2(percentWrapKey);
                
                
                
                
                
                smallcatmiaodto.setAddr(percentWrapKey);
                
                
                
                
                smallcatmiaodto.setComments(percentWrapKey);
                
                
                
            
        }
        //MyBeanUtil.copyProperties(smallcatmiaoreq, smallcatmiaodto);
        Page<SmallCatMiaoDto> smallcatmiaodtoPage = smallcatmiaoserviceimpl.findByPage(smallcatmiaodto, true, pageable);
        MyPageResData<SmallCatMiaoDto> myPageResData = new MyPageResData<>(smallcatmiaodtoPage,
                smallcatmiaoreq.getCurrent(), smallcatmiaoreq.getSize());
        model.addAttribute("smallcatmiaoreq", smallcatmiaoreq);
        model.addAttribute("page", myPageResData);
        return new ModelAndView("comp2/small-cat-miao-list", "smallcatmiaoModel", model);
    }

    @GetMapping("/modify/{id}")
    public ModelAndView modify(@PathVariable("id") Long id, Model model) {
        SmallCatMiaoDto smallcatmiaodto = smallcatmiaoserviceimpl.findById(id);
        model.addAttribute("smallcatmiaodto", smallcatmiaodto);
        return new ModelAndView("comp2/small-cat-miao-modify", "smallcatmiaoModel", model);
    }

    @PostMapping("/modify")
    public ModelAndView saveOrUpdate(SmallCatMiaoDto smallcatmiaodto, HttpServletRequest request) {
        initBaseCreateModifyTs(smallcatmiaodto);
        
            
            
            
            
            
            
            
            
            
            smallcatmiaodto.setPassed(null != request.getParameter("passed"));
            
            
            
            smallcatmiaodto.setPassed2(null != request.getParameter("passed2"));
            
            
        
        SmallCatMiaoDto updatedSmallCatMiaoDto = smallcatmiaoserviceimpl.save(smallcatmiaodto);
        return new ModelAndView("redirect:/api/comp2/small-cat-miao");
    }

    @GetMapping("/remove/{id}")
    public ModelAndView remove(@PathVariable("id") Long id) {
        smallcatmiaoserviceimpl.deleteById(id);
        return new ModelAndView("redirect:/api/comp2/small-cat-miao");
    }

}




