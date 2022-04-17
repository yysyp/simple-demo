

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
import java.util.*;
import java.math.*;

@Slf4j
@RestController
@RequestMapping("/api/company/abc-staff")
public class AbcStaffTfController extends MyBaseController {

    @Autowired
    private AbcStaffServiceImpl abcstaffserviceimpl;

    @GetMapping("/form")
    public ModelAndView createForm(Model model) {
        model.addAttribute("abcstaffdto", new AbcStaffDto());
        return new ModelAndView("company/abc-staff-form", "abcstaffModel", model);
    }

    @PostMapping("/save")
    public ModelAndView save(AbcStaffReq abcstaffreq) {
        AbcStaffDto abcstaffdto = new AbcStaffDto();
        MyBeanUtil.copyProperties(abcstaffreq, abcstaffdto);
        initBaseCreateModifyTs(abcstaffdto);
        AbcStaffDto abcstaffResult = abcstaffserviceimpl.save(abcstaffdto);
        return new ModelAndView("redirect:/api/company/abc-staff");
    }

    @GetMapping("/list")
    public ModelAndView navigateToQuery() {
        return new ModelAndView("redirect:/api/company/abc-staff");
    }

    @GetMapping
    public ModelAndView query(Model model) {
        AbcStaffReq abcstaffreq = new AbcStaffReq();
        model.addAttribute("abcstaffreq", abcstaffreq);
        Pageable pageable = constructPagable(abcstaffreq);
        Page<AbcStaffDto> abcstaffdtoPage = abcstaffserviceimpl.findByPage(pageable);
        MyPageResData<AbcStaffDto> myPageResData = new MyPageResData<>(abcstaffdtoPage,
                abcstaffreq.getCurrent(), abcstaffreq.getSize());
        model.addAttribute("abcstaffreq", abcstaffreq);
        model.addAttribute("page", myPageResData);
        return new ModelAndView("company/abc-staff-list", "abcstaffModel", model);
    }

    @GetMapping("/{id}")
    public ModelAndView getById(@PathVariable("id") Long id, Model model) {
        AbcStaffDto abcstaffdto = abcstaffserviceimpl.findById(id);
        model.addAttribute("abcstaffdto", abcstaffdto);
        return new ModelAndView("company/abc-staff-view", "abcstaffModel", model);
    }

    @PostMapping("/list")
    public ModelAndView list(Model model, AbcStaffReq abcstaffreq) {
        Pageable pageable = constructPagable(abcstaffreq);
        AbcStaffDto abcstaffdto = new AbcStaffDto();
        String key = abcstaffreq.getKey();
        if (StringUtils.isNotBlank(key)) {
            String percentWrapKey = "%" + key + "%";
            
                
                abcstaffdto.setFirstName(percentWrapKey);
                
                
                abcstaffdto.setLastName(percentWrapKey);
                
                
                
                
                
            
        }
        //MyBeanUtil.copyProperties(abcstaffreq, abcstaffdto);
        Page<AbcStaffDto> abcstaffdtoPage = abcstaffserviceimpl.findByPage(abcstaffdto, pageable);
        MyPageResData<AbcStaffDto> myPageResData = new MyPageResData<>(abcstaffdtoPage,
                abcstaffreq.getCurrent(), abcstaffreq.getSize());
        model.addAttribute("abcstaffreq", abcstaffreq);
        model.addAttribute("page", myPageResData);
        return new ModelAndView("company/abc-staff-list", "abcstaffModel", model);
    }

    @GetMapping("/modify/{id}")
    public ModelAndView modify(@PathVariable("id") Long id, Model model) {
        AbcStaffDto abcstaffdto = abcstaffserviceimpl.findById(id);
        model.addAttribute("abcstaffdto", abcstaffdto);
        return new ModelAndView("company/abc-staff-modify", "abcstaffModel", model);
    }

    @PostMapping("/modify")
    public ModelAndView saveOrUpdate(AbcStaffDto abcstaffdto) {
        initBaseCreateModifyTs(abcstaffdto);
        AbcStaffDto updatedAbcStaffDto = abcstaffserviceimpl.save(abcstaffdto);
        return new ModelAndView("redirect:/api/company/abc-staff");
    }

    @GetMapping("/remove/{id}")
    public ModelAndView remove(@PathVariable("id") Long id) {
        abcstaffserviceimpl.deleteById(id);
        return new ModelAndView("redirect:/api/company/abc-staff");
    }

}


