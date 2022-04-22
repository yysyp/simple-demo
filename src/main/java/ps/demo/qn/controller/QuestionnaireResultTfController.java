

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
import ps.demo.qn.dto.QuestionnaireResultDto;
import ps.demo.qn.dto.QuestionnaireResultReq;
import ps.demo.qn.service.QuestionnaireResultServiceImpl;
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
@RequestMapping("/api/qn/questionnaire-result")
public class QuestionnaireResultTfController extends MyBaseController {

    @Autowired
    private QuestionnaireResultServiceImpl questionnaireResultServiceImpl;

    @GetMapping("/form")
    public ModelAndView createForm(Model model) {
        model.addAttribute("questionnaireResultDto", new QuestionnaireResultDto());
        return new ModelAndView("qn/questionnaire-result-form", "questionnaireResultModel", model);
    }

    @PostMapping("/save")
    public ModelAndView save(QuestionnaireResultReq questionnaireResultReq, HttpServletRequest request) {
        QuestionnaireResultDto questionnaireResultDto = new QuestionnaireResultDto();
        

        MyBeanUtil.copyProperties(questionnaireResultReq, questionnaireResultDto);
        initBaseCreateModifyTs(questionnaireResultDto);
        QuestionnaireResultDto questionnaireResultResult = questionnaireResultServiceImpl.save(questionnaireResultDto);
        return new ModelAndView("redirect:/api/qn/questionnaire-result");
    }

    @GetMapping("/list")
    public ModelAndView navigateToQuery() {
        return new ModelAndView("redirect:/api/qn/questionnaire-result");
    }

    @GetMapping
    public ModelAndView query(Model model) {
        QuestionnaireResultReq questionnaireResultReq = new QuestionnaireResultReq();
        model.addAttribute("questionnaireResultReq", questionnaireResultReq);
        Pageable pageable = constructPagable(questionnaireResultReq);
        Page<QuestionnaireResultDto> questionnaireResultDtoPage = questionnaireResultServiceImpl.findInPage(pageable);
        MyPageResData<QuestionnaireResultDto> myPageResData = new MyPageResData<>(questionnaireResultDtoPage,
                questionnaireResultReq.getCurrent(), questionnaireResultReq.getSize());
        model.addAttribute("questionnaireResultReq", questionnaireResultReq);
        model.addAttribute("page", myPageResData);
        return new ModelAndView("qn/questionnaire-result-list", "questionnaireResultModel", model);
    }

    @GetMapping("/{id}")
    public ModelAndView getById(@PathVariable("id") Long id, Model model) {
        QuestionnaireResultDto questionnaireResultDto = questionnaireResultServiceImpl.findById(id);
        model.addAttribute("questionnaireResultDto", questionnaireResultDto);
        return new ModelAndView("qn/questionnaire-result-view", "questionnaireResultModel", model);
    }

    @PostMapping("/list")
    public ModelAndView list(Model model, QuestionnaireResultReq questionnaireResultReq) {
        Pageable pageable = constructPagable(questionnaireResultReq);
        QuestionnaireResultDto questionnaireResultDto = new QuestionnaireResultDto();
        String key = questionnaireResultReq.getKey();
        if (StringUtils.isNotBlank(key)) {
            String percentWrapKey = "%" + key + "%";
            
            questionnaireResultDto.setUri(percentWrapKey);
            questionnaireResultDto.setName(percentWrapKey);
            questionnaireResultDto.setHtmlFile(percentWrapKey);
            questionnaireResultDto.setResponseData(percentWrapKey);

        }
        //MyBeanUtil.copyProperties(questionnaireResultReq, questionnaireResultDto);
        Page<QuestionnaireResultDto> questionnaireResultDtoPage = questionnaireResultServiceImpl.findByAttributesInPage(questionnaireResultDto, true, pageable);
        MyPageResData<QuestionnaireResultDto> myPageResData = new MyPageResData<>(questionnaireResultDtoPage,
                questionnaireResultReq.getCurrent(), questionnaireResultReq.getSize());
        model.addAttribute("questionnaireResultReq", questionnaireResultReq);
        model.addAttribute("page", myPageResData);
        return new ModelAndView("qn/questionnaire-result-list", "questionnaireResultModel", model);
    }

    @GetMapping("/modify/{id}")
    public ModelAndView modify(@PathVariable("id") Long id, Model model) {
        QuestionnaireResultDto questionnaireResultDto = questionnaireResultServiceImpl.findById(id);
        model.addAttribute("questionnaireResultDto", questionnaireResultDto);
        return new ModelAndView("qn/questionnaire-result-modify", "questionnaireResultModel", model);
    }

    @PostMapping("/modify")
    public ModelAndView saveOrUpdate(QuestionnaireResultDto questionnaireResultDto, HttpServletRequest request) {
        initBaseCreateModifyTs(questionnaireResultDto);
        

        QuestionnaireResultDto updatedQuestionnaireResultDto = questionnaireResultServiceImpl.save(questionnaireResultDto);
        return new ModelAndView("redirect:/api/qn/questionnaire-result");
    }

    @GetMapping("/remove/{id}")
    public ModelAndView remove(@PathVariable("id") Long id) {
        questionnaireResultServiceImpl.deleteById(id);
        return new ModelAndView("redirect:/api/qn/questionnaire-result");
    }

}


