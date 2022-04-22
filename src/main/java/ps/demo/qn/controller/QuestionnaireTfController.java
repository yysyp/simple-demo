

package ps.demo.qn.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import ps.demo.account.helper.MyErrorUtils;
import ps.demo.common.MyBaseController;
import ps.demo.common.MyPageReq;
import ps.demo.common.MyPageResData;
import ps.demo.exception.BadRequestException;
import ps.demo.exception.CodeEnum;
import ps.demo.qn.dto.QuestionnaireDto;
import ps.demo.qn.dto.QuestionnaireReq;
import ps.demo.qn.service.QuestionnaireServiceImpl;
import ps.demo.util.MyBeanUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ps.demo.util.MyFileUtil;
import ps.demo.util.MyReadWriteUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.math.*;

@Slf4j
@RestController
@RequestMapping("/api/qn/questionnaire")
public class QuestionnaireTfController extends MyBaseController {

    @Value("${dir.upload-folder}")
    private String uploadDir;

    @Autowired
    private QuestionnaireServiceImpl questionnaireServiceImpl;

    @GetMapping("/form")
    public ModelAndView createForm(Model model) {
        model.addAttribute("questionnaireDto", new QuestionnaireDto());
        return new ModelAndView("qn/questionnaire-form", "questionnaireModel", model);
    }

    @PostMapping("/save")
    public ModelAndView save(Model model, QuestionnaireReq questionnaireReq, HttpServletRequest request) {
        QuestionnaireDto questionnaireDto = new QuestionnaireDto();
        
        questionnaireDto.setWholeHtml(null != request.getParameter("wholeHtml"));

        MyBeanUtil.copyProperties(questionnaireReq, questionnaireDto);
        if(CollectionUtils.isNotEmpty(questionnaireServiceImpl.findByAttribute("uri", questionnaireDto.getUri()))
        || CollectionUtils.isNotEmpty(questionnaireServiceImpl.findByAttribute("name", questionnaireDto.getName()))) {
            MyErrorUtils.setLastError(new BadRequestException(CodeEnum.DUPLICATED_KEY), request);
            model.addAttribute("questionnaireDto", questionnaireDto);
            return new ModelAndView("qn/questionnaire-form", "questionnaireModel", model);
        }
        String fileName = UUID.randomUUID().toString();
        questionnaireDto.setHtmlFile(fileName);
        File htmlFile = new File(uploadDir, fileName);
        MyReadWriteUtil.writeFileContent(htmlFile, questionnaireDto.getHtmlContent());
        questionnaireDto.setHtmlContent("");
        initBaseCreateModifyTs(questionnaireDto);
        QuestionnaireDto questionnaireResult = questionnaireServiceImpl.save(questionnaireDto);
        return new ModelAndView("redirect:/api/qn/questionnaire");
    }

    @GetMapping("/list")
    public ModelAndView navigateToQuery() {
        return new ModelAndView("redirect:/api/qn/questionnaire");
    }

    @GetMapping
    public ModelAndView query(Model model) {
        QuestionnaireReq questionnaireReq = new QuestionnaireReq();
        model.addAttribute("questionnaireReq", questionnaireReq);
        Pageable pageable = constructPagable(questionnaireReq);
        Page<QuestionnaireDto> questionnaireDtoPage = questionnaireServiceImpl.findInPage(pageable);
        MyPageResData<QuestionnaireDto> myPageResData = new MyPageResData<>(questionnaireDtoPage,
                questionnaireReq.getCurrent(), questionnaireReq.getSize());
        model.addAttribute("questionnaireReq", questionnaireReq);
        model.addAttribute("page", myPageResData);
        return new ModelAndView("qn/questionnaire-list", "questionnaireModel", model);
    }

    @GetMapping("/{id}")
    public ModelAndView getById(@PathVariable("id") Long id, Model model) {
        QuestionnaireDto questionnaireDto = questionnaireServiceImpl.findById(id);
        File htmlFile = new File(uploadDir, questionnaireDto.getHtmlFile());
        questionnaireDto.setHtmlContent(MyReadWriteUtil.readFileContent(htmlFile));
        model.addAttribute("questionnaireDto", questionnaireDto);
        return new ModelAndView("qn/questionnaire-view", "questionnaireModel", model);
    }

    @PostMapping("/list")
    public ModelAndView list(Model model, QuestionnaireReq questionnaireReq) {
        Pageable pageable = constructPagable(questionnaireReq);
        QuestionnaireDto questionnaireDto = new QuestionnaireDto();
        String key = questionnaireReq.getKey();
        if (StringUtils.isNotBlank(key)) {
            String percentWrapKey = "%" + key + "%";
            
            questionnaireDto.setUri(percentWrapKey);
            questionnaireDto.setName(percentWrapKey);
            questionnaireDto.setHtmlFile(percentWrapKey);
            questionnaireDto.setFormItemNames(percentWrapKey);
            questionnaireDto.setHtmlContent(percentWrapKey);

        }
        //MyBeanUtil.copyProperties(questionnaireReq, questionnaireDto);
        Page<QuestionnaireDto> questionnaireDtoPage = questionnaireServiceImpl.findByAttributesInPage(questionnaireDto, true, pageable);
        MyPageResData<QuestionnaireDto> myPageResData = new MyPageResData<>(questionnaireDtoPage,
                questionnaireReq.getCurrent(), questionnaireReq.getSize());
        model.addAttribute("questionnaireReq", questionnaireReq);
        model.addAttribute("page", myPageResData);
        return new ModelAndView("qn/questionnaire-list", "questionnaireModel", model);
    }

    @GetMapping("/modify/{id}")
    public ModelAndView modify(@PathVariable("id") Long id, Model model) {
        QuestionnaireDto questionnaireDto = questionnaireServiceImpl.findById(id);
        File htmlFile = new File(uploadDir, questionnaireDto.getHtmlFile());
        questionnaireDto.setHtmlContent(MyReadWriteUtil.readFileContent(htmlFile));
        model.addAttribute("questionnaireDto", questionnaireDto);
        return new ModelAndView("qn/questionnaire-modify", "questionnaireModel", model);
    }

    @PostMapping("/modify")
    public ModelAndView saveOrUpdate(Model model, QuestionnaireDto questionnaireDto, HttpServletRequest request) {
        initBaseCreateModifyTs(questionnaireDto);
        
        questionnaireDto.setWholeHtml(null != request.getParameter("wholeHtml"));
        List<QuestionnaireDto> existingCheck = questionnaireServiceImpl.findByAttribute("uri", questionnaireDto.getUri());
        List<QuestionnaireDto> existingCheck2 = questionnaireServiceImpl.findByAttribute("name", questionnaireDto.getName());
        if((CollectionUtils.isNotEmpty(existingCheck) && existingCheck.size() > 1)
        || (CollectionUtils.isNotEmpty(existingCheck2) && existingCheck2.size() > 1)) {
            MyErrorUtils.setLastError(new BadRequestException(CodeEnum.DUPLICATED_KEY), request);
            model.addAttribute("questionnaireDto", questionnaireDto);
            return new ModelAndView("qn/questionnaire-form", "questionnaireModel", model);
        }
        File htmlFile = new File(uploadDir, questionnaireDto.getHtmlFile());
        MyReadWriteUtil.writeFileContent(htmlFile, questionnaireDto.getHtmlContent());
        questionnaireDto.setHtmlContent("");
        QuestionnaireDto updatedQuestionnaireDto = questionnaireServiceImpl.save(questionnaireDto);
        return new ModelAndView("redirect:/api/qn/questionnaire");
    }

    @GetMapping("/remove/{id}")
    public ModelAndView remove(@PathVariable("id") Long id) {
        questionnaireServiceImpl.deleteById(id);
        return new ModelAndView("redirect:/api/qn/questionnaire");
    }

}


