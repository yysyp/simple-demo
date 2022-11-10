

package ps.demo.newstock.controller;

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
import ps.demo.newstock.dto.NewStockDataDto;
import ps.demo.newstock.dto.NewStockDataReq;
import ps.demo.newstock.service.NewStockDataServiceImpl;
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
@RequestMapping("/api/newstock/new-stock-data")
public class NewStockDataTfController extends MyBaseController {

    @Autowired
    private NewStockDataServiceImpl newStockDataServiceImpl;

    @GetMapping("/form")
    public ModelAndView createForm(Model model) {
        model.addAttribute("newStockDataDto", new NewStockDataDto());
        return new ModelAndView("newstock/new-stock-data-form", "newStockDataModel", model);
    }

    @PostMapping("/save")
    public ModelAndView save(NewStockDataReq newStockDataReq, HttpServletRequest request) {
        NewStockDataDto newStockDataDto = new NewStockDataDto();
        

        MyBeanUtil.copyProperties(newStockDataReq, newStockDataDto);
        initBaseCreateModifyTs(newStockDataDto);
        NewStockDataDto newStockDataResult = newStockDataServiceImpl.save(newStockDataDto);
        return new ModelAndView("redirect:/api/newstock/new-stock-data");
    }

    @GetMapping("/list")
    public ModelAndView navigateToQuery() {
        return new ModelAndView("redirect:/api/newstock/new-stock-data");
    }

    @GetMapping
    public ModelAndView query(Model model) {
        NewStockDataReq newStockDataReq = new NewStockDataReq();
        model.addAttribute("newStockDataReq", newStockDataReq);
        Pageable pageable = constructPagable(newStockDataReq);
        Page<NewStockDataDto> newStockDataDtoPage = newStockDataServiceImpl.findInPage(pageable);
        MyPageResData<NewStockDataDto> myPageResData = new MyPageResData<>(newStockDataDtoPage,
                newStockDataReq.getCurrent(), newStockDataReq.getSize());
        model.addAttribute("newStockDataReq", newStockDataReq);
        model.addAttribute("page", myPageResData);
        return new ModelAndView("newstock/new-stock-data-list", "newStockDataModel", model);
    }

    @GetMapping("/{id}")
    public ModelAndView getById(@PathVariable("id") Long id, Model model) {
        NewStockDataDto newStockDataDto = newStockDataServiceImpl.findById(id);
        model.addAttribute("newStockDataDto", newStockDataDto);
        return new ModelAndView("newstock/new-stock-data-view", "newStockDataModel", model);
    }

    @PostMapping("/list")
    public ModelAndView list(Model model, NewStockDataReq newStockDataReq) {
        Pageable pageable = constructPagable(newStockDataReq);
        NewStockDataDto newStockDataDto = new NewStockDataDto();
        String key = newStockDataReq.getKey();
        if (StringUtils.isNotBlank(key)) {
            String percentWrapKey = "%" + key + "%";
            
            newStockDataDto.setCompanyCode(percentWrapKey);
            newStockDataDto.setCompanyName(percentWrapKey);
            newStockDataDto.setRawPeriod(percentWrapKey);
            newStockDataDto.setRawKemu(percentWrapKey);
            newStockDataDto.setKemu(percentWrapKey);
            newStockDataDto.setRawKemuValue(percentWrapKey);
            newStockDataDto.setFileName(percentWrapKey);
            newStockDataDto.setComments(percentWrapKey);

        }
        //MyBeanUtil.copyProperties(newStockDataReq, newStockDataDto);
        Page<NewStockDataDto> newStockDataDtoPage = newStockDataServiceImpl.findByAttributesInPage(newStockDataDto, true, pageable);
        MyPageResData<NewStockDataDto> myPageResData = new MyPageResData<>(newStockDataDtoPage,
                newStockDataReq.getCurrent(), newStockDataReq.getSize());
        model.addAttribute("newStockDataReq", newStockDataReq);
        model.addAttribute("page", myPageResData);
        return new ModelAndView("newstock/new-stock-data-list", "newStockDataModel", model);
    }

    @GetMapping("/modify/{id}")
    public ModelAndView modify(@PathVariable("id") Long id, Model model) {
        NewStockDataDto newStockDataDto = newStockDataServiceImpl.findById(id);
        model.addAttribute("newStockDataDto", newStockDataDto);
        return new ModelAndView("newstock/new-stock-data-modify", "newStockDataModel", model);
    }

    @PostMapping("/modify")
    public ModelAndView saveOrUpdate(NewStockDataDto newStockDataDto, HttpServletRequest request) {
        initBaseCreateModifyTs(newStockDataDto);
        

        NewStockDataDto updatedNewStockDataDto = newStockDataServiceImpl.save(newStockDataDto);
        return new ModelAndView("redirect:/api/newstock/new-stock-data");
    }

    @GetMapping("/remove/{id}")
    public ModelAndView remove(@PathVariable("id") Long id) {
        newStockDataServiceImpl.deleteById(id);
        return new ModelAndView("redirect:/api/newstock/new-stock-data");
    }

}


