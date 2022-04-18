

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
        model.addAttribute("questionnairedto", new QuestionnaireDto());
        return new ModelAndView("qn/questionnaire-form", "questionnaireModel", model);
    }

    @PostMapping("/save")
    public ModelAndView save(QuestionnaireReq questionnairereq) {
        QuestionnaireDto questionnairedto = new QuestionnaireDto();
        MyBeanUtil.copyProperties(questionnairereq, questionnairedto);
        initBaseCreateModifyTs(questionnairedto);

        String filename = UUID.randomUUID().toString();
        File file = new File(uploadDir, filename);
        MyReadWriteUtil.writeFileContent(file, questionnairedto.getLayoutitContent(), "UTF-8");
        questionnairedto.setLayoutitContent(filename);

        QuestionnaireDto questionnaireResult = questionnaireserviceimpl.save(questionnairedto);
        return new ModelAndView("redirect:/api/qn/questionnaire");
    }

    @GetMapping("/list")
    public ModelAndView navigateToQuery() {
        return new ModelAndView("redirect:/api/qn/questionnaire");
    }

    @GetMapping
    public ModelAndView query(Model model) {
        QuestionnaireReq questionnairereq = new QuestionnaireReq();
        model.addAttribute("questionnairereq", questionnairereq);
        Pageable pageable = constructPagable(questionnairereq);
        Page<QuestionnaireDto> questionnairedtoPage = questionnaireserviceimpl.findByPage(pageable);
        MyPageResData<QuestionnaireDto> myPageResData = new MyPageResData<>(questionnairedtoPage,
                questionnairereq.getCurrent(), questionnairereq.getSize());
        model.addAttribute("questionnairereq", questionnairereq);
        model.addAttribute("page", myPageResData);
        return new ModelAndView("qn/questionnaire-list", "questionnaireModel", model);
    }

    @GetMapping("/{id}")
    public ModelAndView getById(@PathVariable("id") Long id, Model model) {
        QuestionnaireDto questionnairedto = questionnaireserviceimpl.findById(id);
        model.addAttribute("questionnairedto", questionnairedto);
        return new ModelAndView("qn/questionnaire-view", "questionnaireModel", model);
    }

    @PostMapping("/list")
    public ModelAndView list(Model model, QuestionnaireReq questionnairereq) {
        Pageable pageable = constructPagable(questionnairereq);
        QuestionnaireDto questionnairedto = new QuestionnaireDto();
        String key = questionnairereq.getKey();
        if (StringUtils.isNotBlank(key)) {
            String percentWrapKey = "%" + key + "%";
            
                
                questionnairedto.setUri(percentWrapKey);
                
                
                questionnairedto.setLayoutitContent(percentWrapKey);
                
            
        }
        //MyBeanUtil.copyProperties(questionnairereq, questionnairedto);
        Page<QuestionnaireDto> questionnairedtoPage = questionnaireserviceimpl.findByPage(questionnairedto, pageable);
        MyPageResData<QuestionnaireDto> myPageResData = new MyPageResData<>(questionnairedtoPage,
                questionnairereq.getCurrent(), questionnairereq.getSize());
        model.addAttribute("questionnairereq", questionnairereq);
        model.addAttribute("page", myPageResData);
        return new ModelAndView("qn/questionnaire-list", "questionnaireModel", model);
    }

    @GetMapping("/modify/{id}")
    public ModelAndView modify(@PathVariable("id") Long id, Model model) {
        QuestionnaireDto questionnairedto = questionnaireserviceimpl.findById(id);
        model.addAttribute("questionnairedto", questionnairedto);
        return new ModelAndView("qn/questionnaire-modify", "questionnaireModel", model);
    }

    @PostMapping("/modify")
    public ModelAndView saveOrUpdate(QuestionnaireDto questionnairedto) {
        initBaseCreateModifyTs(questionnairedto);
        //delete old
        QuestionnaireDto oldDto = questionnaireserviceimpl.findById(questionnairedto.getId());
        UUID uuid = UUID.fromString(oldDto.getLayoutitContent());
        File oldFile = new File(uploadDir, uuid.toString());
        if (oldFile.exists() && oldFile.isFile()) {
            oldFile.delete();
        }
        //save new
        String filename = UUID.randomUUID().toString();
        File file = new File(uploadDir, filename);
        MyReadWriteUtil.writeFileContent(file, questionnairedto.getLayoutitContent(), "UTF-8");
        questionnairedto.setLayoutitContent(filename);

        QuestionnaireDto updatedQuestionnaireDto = questionnaireserviceimpl.save(questionnairedto);
        return new ModelAndView("redirect:/api/qn/questionnaire");
    }

    @GetMapping("/remove/{id}")
    public ModelAndView remove(@PathVariable("id") Long id) {
        questionnaireserviceimpl.deleteById(id);
        return new ModelAndView("redirect:/api/qn/questionnaire");
    }

}


