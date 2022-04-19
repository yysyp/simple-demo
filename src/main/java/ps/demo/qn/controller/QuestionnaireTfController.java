

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
import ps.demo.common.MyBaseController;
import ps.demo.common.MyPageReq;
import ps.demo.common.MyPageResData;
import ps.demo.qn.dto.QuestionnaireDto;
import ps.demo.qn.dto.QuestionnaireReq;
import ps.demo.qn.service.QuestionnaireServiceImpl;
import ps.demo.util.MyBeanUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import lombok.*;
import ps.demo.util.MyFileUtil;
import ps.demo.util.MyReadWriteUtil;

import java.util.*;
import java.math.*;

@Slf4j
@RestController
@RequestMapping("/api/qn/questionnaire")
public class QuestionnaireTfController extends MyBaseController {

    @Value("${dir.upload-folder}")
    private String uploadDir;

    @Autowired
    private QuestionnaireServiceImpl questionnaireserviceimpl;

    @GetMapping("/form")
    public ModelAndView createForm(Model model) {
        model.addAttribute("questionnaireDto", new QuestionnaireDto());
        return new ModelAndView("qn/questionnaire-form", "questionnaireModel", model);
    }

    @PostMapping("/save")
    public ModelAndView save(QuestionnaireReq questionnaireReq) {
        QuestionnaireDto questionnaireDto = new QuestionnaireDto();
        MyBeanUtil.copyProperties(questionnaireReq, questionnaireDto);
        initBaseCreateModifyTs(questionnaireDto);

        String filename = UUID.randomUUID().toString();
        File file = new File(uploadDir, filename);
        MyReadWriteUtil.writeFileContent(file, questionnaireDto.getLayoutitContent(), "UTF-8");
        questionnaireDto.setLayoutitContent(filename);

        QuestionnaireDto questionnaireResult = questionnaireserviceimpl.save(questionnaireDto);
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
        Page<QuestionnaireDto> questionnaireDtoPage = questionnaireserviceimpl.findByPage(pageable);
        MyPageResData<QuestionnaireDto> myPageResData = new MyPageResData<>(questionnaireDtoPage,
                questionnaireReq.getCurrent(), questionnaireReq.getSize());
        model.addAttribute("questionnaireReq", questionnaireReq);
        model.addAttribute("page", myPageResData);
        return new ModelAndView("qn/questionnaire-list", "questionnaireModel", model);
    }

    @GetMapping("/{id}")
    public ModelAndView getById(@PathVariable("id") Long id, Model model) {
        QuestionnaireDto questionnaireDto = questionnaireserviceimpl.findById(id);
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
                
                
                questionnaireDto.setLayoutitContent(percentWrapKey);
                
            
        }
        //MyBeanUtil.copyProperties(questionnaireReq, questionnaireDto);
        Page<QuestionnaireDto> questionnaireDtoPage = questionnaireserviceimpl.findByPage(questionnaireDto, true, pageable);
        MyPageResData<QuestionnaireDto> myPageResData = new MyPageResData<>(questionnaireDtoPage,
                questionnaireReq.getCurrent(), questionnaireReq.getSize());
        model.addAttribute("questionnaireReq", questionnaireReq);
        model.addAttribute("page", myPageResData);
        return new ModelAndView("qn/questionnaire-list", "questionnaireModel", model);
    }

    @GetMapping("/modify/{id}")
    public ModelAndView modify(@PathVariable("id") Long id, Model model) {
        QuestionnaireDto questionnaireDto = questionnaireserviceimpl.findById(id);
        model.addAttribute("questionnaireDto", questionnaireDto);
        return new ModelAndView("qn/questionnaire-modify", "questionnaireModel", model);
    }

    @PostMapping("/modify")
    public ModelAndView saveOrUpdate(QuestionnaireDto questionnaireDto) {
        initBaseCreateModifyTs(questionnaireDto);
        //delete old
        QuestionnaireDto oldDto = questionnaireserviceimpl.findById(questionnaireDto.getId());
        UUID uuid = UUID.fromString(oldDto.getLayoutitContent());
        File oldFile = new File(uploadDir, uuid.toString());
        if (oldFile.exists() && oldFile.isFile()) {
            oldFile.delete();
        }
        //save new
        String filename = UUID.randomUUID().toString();
        File file = new File(uploadDir, filename);
        MyReadWriteUtil.writeFileContent(file, questionnaireDto.getLayoutitContent(), "UTF-8");
        questionnaireDto.setLayoutitContent(filename);

        QuestionnaireDto updatedquestionnaireDto = questionnaireserviceimpl.save(questionnaireDto);
        return new ModelAndView("redirect:/api/qn/questionnaire");
    }

    @GetMapping("/remove/{id}")
    public ModelAndView remove(@PathVariable("id") Long id) {
        questionnaireserviceimpl.deleteById(id);
        return new ModelAndView("redirect:/api/qn/questionnaire");
    }

}


