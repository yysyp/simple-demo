

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
import java.util.*;
import java.math.*;

@Slf4j
@RestController
@RequestMapping("/api/qn/questionnaire-response")
public class QuestionnaireResponseTfController extends MyBaseController {

    @Autowired
    private QuestionnaireResponseServiceImpl questionnaireresponseserviceimpl;

    @GetMapping("/form")
    public ModelAndView createForm(Model model) {
        model.addAttribute("questionnaireresponsedto", new QuestionnaireResponseDto());
        return new ModelAndView("qn/questionnaire-response-form", "questionnaireresponseModel", model);
    }

    @PostMapping("/save")
    public ModelAndView save(QuestionnaireResponseReq questionnaireresponsereq) {
        QuestionnaireResponseDto questionnaireresponsedto = new QuestionnaireResponseDto();
        MyBeanUtil.copyProperties(questionnaireresponsereq, questionnaireresponsedto);
        initBaseCreateModifyTs(questionnaireresponsedto);
        QuestionnaireResponseDto questionnaireresponseResult = questionnaireresponseserviceimpl.save(questionnaireresponsedto);
        return new ModelAndView("redirect:/api/qn/questionnaire-response");
    }

    @GetMapping("/list")
    public ModelAndView navigateToQuery() {
        return new ModelAndView("redirect:/api/qn/questionnaire-response");
    }

    @GetMapping
    public ModelAndView query(Model model) {
        QuestionnaireResponseReq questionnaireresponsereq = new QuestionnaireResponseReq();
        model.addAttribute("questionnaireresponsereq", questionnaireresponsereq);
        Pageable pageable = constructPagable(questionnaireresponsereq);
        Page<QuestionnaireResponseDto> questionnaireresponsedtoPage = questionnaireresponseserviceimpl.findByPage(pageable);
        MyPageResData<QuestionnaireResponseDto> myPageResData = new MyPageResData<>(questionnaireresponsedtoPage,
                questionnaireresponsereq.getCurrent(), questionnaireresponsereq.getSize());
        model.addAttribute("questionnaireresponsereq", questionnaireresponsereq);
        model.addAttribute("page", myPageResData);
        return new ModelAndView("qn/questionnaire-response-list", "questionnaireresponseModel", model);
    }

    @GetMapping("/{id}")
    public ModelAndView getById(@PathVariable("id") Long id, Model model) {
        QuestionnaireResponseDto questionnaireresponsedto = questionnaireresponseserviceimpl.findById(id);
        model.addAttribute("questionnaireresponsedto", questionnaireresponsedto);
        return new ModelAndView("qn/questionnaire-response-view", "questionnaireresponseModel", model);
    }

    @PostMapping("/list")
    public ModelAndView list(Model model, QuestionnaireResponseReq questionnaireresponsereq) {
        Pageable pageable = constructPagable(questionnaireresponsereq);
        QuestionnaireResponseDto questionnaireresponsedto = new QuestionnaireResponseDto();
        String key = questionnaireresponsereq.getKey();
        if (StringUtils.isNotBlank(key)) {
            String percentWrapKey = "%" + key + "%";
            
                
                questionnaireresponsedto.setUri(percentWrapKey);
                
                
                questionnaireresponsedto.setResponseContent(percentWrapKey);
                
            
        }
        //MyBeanUtil.copyProperties(questionnaireresponsereq, questionnaireresponsedto);
        Page<QuestionnaireResponseDto> questionnaireresponsedtoPage = questionnaireresponseserviceimpl.findByPage(questionnaireresponsedto, pageable);
        MyPageResData<QuestionnaireResponseDto> myPageResData = new MyPageResData<>(questionnaireresponsedtoPage,
                questionnaireresponsereq.getCurrent(), questionnaireresponsereq.getSize());
        model.addAttribute("questionnaireresponsereq", questionnaireresponsereq);
        model.addAttribute("page", myPageResData);
        return new ModelAndView("qn/questionnaire-response-list", "questionnaireresponseModel", model);
    }

    @GetMapping("/modify/{id}")
    public ModelAndView modify(@PathVariable("id") Long id, Model model) {
        QuestionnaireResponseDto questionnaireresponsedto = questionnaireresponseserviceimpl.findById(id);
        model.addAttribute("questionnaireresponsedto", questionnaireresponsedto);
        return new ModelAndView("qn/questionnaire-response-modify", "questionnaireresponseModel", model);
    }

    @PostMapping("/modify")
    public ModelAndView saveOrUpdate(QuestionnaireResponseDto questionnaireresponsedto) {
        initBaseCreateModifyTs(questionnaireresponsedto);
        QuestionnaireResponseDto updatedQuestionnaireResponseDto = questionnaireresponseserviceimpl.save(questionnaireresponsedto);
        return new ModelAndView("redirect:/api/qn/questionnaire-response");
    }

    @GetMapping("/remove/{id}")
    public ModelAndView remove(@PathVariable("id") Long id) {
        questionnaireresponseserviceimpl.deleteById(id);
        return new ModelAndView("redirect:/api/qn/questionnaire-response");
    }

}


