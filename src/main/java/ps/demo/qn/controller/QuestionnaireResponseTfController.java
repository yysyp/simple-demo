



package ps.demo.qn.controller;

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
import ps.demo.qn.dto.QuestionnaireResponseDto;
import ps.demo.qn.dto.QuestionnaireResponseReq;
import ps.demo.qn.service.QuestionnaireResponseServiceImpl;
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
@RequestMapping("/api/qn/questionnaire-response")
public class QuestionnaireResponseTfController extends MyBaseController {

    @Autowired
    private QuestionnaireResponseServiceImpl questionnaireResponseServiceImpl;

    @GetMapping("/form")
    public ModelAndView createForm(Model model) {
        model.addAttribute("questionnaireResponseDto", new QuestionnaireResponseDto());
        return new ModelAndView("qn/questionnaire-response-form", "questionnaireResponseModel", model);
    }

    @PostMapping("/save")
    public ModelAndView save(QuestionnaireResponseReq questionnaireResponseReq, HttpServletRequest request) {
        QuestionnaireResponseDto questionnaireResponseDto = new QuestionnaireResponseDto();
        
            
            
        
        MyBeanUtil.copyProperties(questionnaireResponseReq, questionnaireResponseDto);
        initBaseCreateModifyTs(questionnaireResponseDto);
        QuestionnaireResponseDto questionnaireResponseResult = questionnaireResponseServiceImpl.save(questionnaireResponseDto);
        return new ModelAndView("redirect:/api/qn/questionnaire-response");
    }

    @GetMapping("/list")
    public ModelAndView navigateToQuery() {
        return new ModelAndView("redirect:/api/qn/questionnaire-response");
    }

    @GetMapping
    public ModelAndView query(Model model) {
        QuestionnaireResponseReq questionnaireResponseReq = new QuestionnaireResponseReq();
        model.addAttribute("questionnaireResponseReq", questionnaireResponseReq);
        Pageable pageable = constructPagable(questionnaireResponseReq);
        Page<QuestionnaireResponseDto> questionnaireResponseDtoPage = questionnaireResponseServiceImpl.findByPage(pageable);
        MyPageResData<QuestionnaireResponseDto> myPageResData = new MyPageResData<>(questionnaireResponseDtoPage,
                questionnaireResponseReq.getCurrent(), questionnaireResponseReq.getSize());
        model.addAttribute("questionnaireResponseReq", questionnaireResponseReq);
        model.addAttribute("page", myPageResData);
        return new ModelAndView("qn/questionnaire-response-list", "questionnaireResponseModel", model);
    }

    @GetMapping("/{id}")
    public ModelAndView getById(@PathVariable("id") Long id, Model model) {
        QuestionnaireResponseDto questionnaireResponseDto = questionnaireResponseServiceImpl.findById(id);
        model.addAttribute("questionnaireResponseDto", questionnaireResponseDto);
        return new ModelAndView("qn/questionnaire-response-view", "questionnaireResponseModel", model);
    }

    @PostMapping("/list")
    public ModelAndView list(Model model, QuestionnaireResponseReq questionnaireResponseReq) {
        Pageable pageable = constructPagable(questionnaireResponseReq);
        QuestionnaireResponseDto questionnaireResponseDto = new QuestionnaireResponseDto();
        String key = questionnaireResponseReq.getKey();
        if (StringUtils.isNotBlank(key)) {
            String percentWrapKey = "%" + key + "%";
            
                
                questionnaireResponseDto.setUri(percentWrapKey);
                
                
                questionnaireResponseDto.setResponseContent(percentWrapKey);
                
            
        }
        //MyBeanUtil.copyProperties(questionnaireResponseReq, questionnaireResponseDto);
        Page<QuestionnaireResponseDto> questionnaireResponseDtoPage = questionnaireResponseServiceImpl.findByPage(questionnaireResponseDto, true, pageable);
        MyPageResData<QuestionnaireResponseDto> myPageResData = new MyPageResData<>(questionnaireResponseDtoPage,
                questionnaireResponseReq.getCurrent(), questionnaireResponseReq.getSize());
        model.addAttribute("questionnaireResponseReq", questionnaireResponseReq);
        model.addAttribute("page", myPageResData);
        return new ModelAndView("qn/questionnaire-response-list", "questionnaireResponseModel", model);
    }

    @GetMapping("/modify/{id}")
    public ModelAndView modify(@PathVariable("id") Long id, Model model) {
        QuestionnaireResponseDto questionnaireResponseDto = questionnaireResponseServiceImpl.findById(id);
        model.addAttribute("questionnaireResponseDto", questionnaireResponseDto);
        return new ModelAndView("qn/questionnaire-response-modify", "questionnaireResponseModel", model);
    }

    @PostMapping("/modify")
    public ModelAndView saveOrUpdate(QuestionnaireResponseDto questionnaireResponseDto, HttpServletRequest request) {
        initBaseCreateModifyTs(questionnaireResponseDto);
        
            
            
        
        QuestionnaireResponseDto updatedQuestionnaireResponseDto = questionnaireResponseServiceImpl.save(questionnaireResponseDto);
        return new ModelAndView("redirect:/api/qn/questionnaire-response");
    }

    @GetMapping("/remove/{id}")
    public ModelAndView remove(@PathVariable("id") Long id) {
        questionnaireResponseServiceImpl.deleteById(id);
        return new ModelAndView("redirect:/api/qn/questionnaire-response");
    }

}




