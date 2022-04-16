package ps.demo.system.controller;

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
import ps.demo.system.dto.SystemTrackingDto;
import ps.demo.system.dto.SystemTrackingReq;
import ps.demo.system.service.impl.SystemTrackingServiceImpl;
import ps.demo.util.MyBeanUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/system/system-tracking")
public class SystemTrackingTfController extends MyBaseController {

    @Autowired
    private SystemTrackingServiceImpl systemTrackingServiceImpl;

    @GetMapping("/form")
    public ModelAndView createForm(Model model) {
        model.addAttribute("systemTrackingDto", new SystemTrackingDto());
        return new ModelAndView("system/system-tracking-form", "systemTrackingModel", model);
    }

    @PostMapping("/save")
    public ModelAndView save(SystemTrackingReq systemTrackingReq) {
        SystemTrackingDto systemTrackingDto = new SystemTrackingDto();
        MyBeanUtil.copyProperties(systemTrackingReq, systemTrackingDto);
        SystemTrackingDto systemTrackingResult = systemTrackingServiceImpl.save(systemTrackingDto);
        return new ModelAndView("redirect:/api/system/system-tracking");
    }

    @GetMapping("/list")
    public ModelAndView navigateToQuery() {
        return new ModelAndView("redirect:/api/system/system-tracking");
    }

    @GetMapping
    public ModelAndView query(Model model) {
        SystemTrackingReq systemTrackingReq = new SystemTrackingReq();
        model.addAttribute("systemTrackingReq", systemTrackingReq);
        Pageable pageable = constructPagable(systemTrackingReq);
        Page<SystemTrackingDto> systemTrackingDtoPage = systemTrackingServiceImpl.findByPage(pageable);
        MyPageResData<SystemTrackingDto> myPageResData = new MyPageResData<>(systemTrackingDtoPage,
                systemTrackingReq.getCurrent(), systemTrackingReq.getSize());
        model.addAttribute("systemTrackingReq", systemTrackingReq);
        model.addAttribute("page", myPageResData);
        return new ModelAndView("system/system-tracking-list", "systemTrackingModel", model);
    }

    @GetMapping("/{id}")
    public ModelAndView getById(@PathVariable("id") Long id, Model model) {
        SystemTrackingDto systemTrackingDto = systemTrackingServiceImpl.findById(id);
        model.addAttribute("systemTrackingDto", systemTrackingDto);
        return new ModelAndView("system/system-tracking-view", "systemTrackingModel", model);
    }

    @PostMapping("/list")
    public ModelAndView list(Model model, SystemTrackingReq systemTrackingReq) {
        Pageable pageable = constructPagable(systemTrackingReq);
        SystemTrackingDto systemTrackingDto = new SystemTrackingDto();
        String key = systemTrackingReq.getKey();
        if (StringUtils.isNotBlank(key)) {
            systemTrackingDto.setCountSource(key);
            systemTrackingDto.setFetchSourceByPage(key);
        }
        //MyBeanUtil.copyProperties(systemTrackingReq, systemTrackingDto);
        Page<SystemTrackingDto> systemTrackingDtoPage = systemTrackingServiceImpl.findByPage(systemTrackingDto, pageable);
        MyPageResData<SystemTrackingDto> myPageResData = new MyPageResData<>(systemTrackingDtoPage,
                systemTrackingReq.getCurrent(), systemTrackingReq.getSize());
        model.addAttribute("systemTrackingReq", systemTrackingReq);
        model.addAttribute("page", myPageResData);
        return new ModelAndView("system/system-tracking-list", "systemTrackingModel", model);
    }

    @GetMapping("/modify/{id}")
    public ModelAndView modify(@PathVariable("id") Long id, Model model) {
        SystemTrackingDto systemTrackingDto = systemTrackingServiceImpl.findById(id);
        model.addAttribute("systemTrackingDto", systemTrackingDto);
        return new ModelAndView("system/system-tracking-modify", "systemTrackingModel", model);
    }

    @PostMapping("/modify")
    public ModelAndView saveOrUpdate(SystemTrackingDto systemTrackingDto) {
        SystemTrackingDto updatedSystemTrackingDto = systemTrackingServiceImpl.save(systemTrackingDto);
        return new ModelAndView("redirect:/api/system/system-tracking");
    }

    @GetMapping("/remove/{id}")
    public ModelAndView remove(@PathVariable("id") Long id) {
        systemTrackingServiceImpl.deleteById(id);
        return new ModelAndView("redirect:/api/system/system-tracking");
    }

}
