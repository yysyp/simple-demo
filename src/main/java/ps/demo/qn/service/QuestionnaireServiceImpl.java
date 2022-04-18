

package ps.demo.qn.service;

import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Collection;

import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.math.*;

import java.util.stream.Collectors;

import com.google.common.collect.ImmutableMap;
import org.springframework.transaction.annotation.Transactional;
import ps.demo.order.repository.NewCartRepository;
import ps.demo.qn.dto.QuestionnaireDto;
import ps.demo.qn.dto.QuestionnaireReq;
import ps.demo.qn.entity.Questionnaire;
import ps.demo.qn.repository.QuestionnaireDao;
import ps.demo.util.MyBeanUtil;

import javax.persistence.criteria.*;
import lombok.*;
import java.util.*;
import java.math.*;

@Slf4j
@Service
public class QuestionnaireServiceImpl {

    @Autowired
    QuestionnaireDao questionnairedao;

    @Transactional
    public QuestionnaireDto save(QuestionnaireDto questionnairedto) {
        Questionnaire questionnaire = new Questionnaire();
        MyBeanUtil.copyProperties(questionnairedto, questionnaire);
        Questionnaire entity = questionnairedao.save(questionnaire);
        MyBeanUtil.copyProperties(entity, questionnairedto);
        return questionnairedto;
    }

    @Transactional(readOnly = true)
    public List<QuestionnaireDto> findAll() {
        List<Questionnaire> questionnaireList = questionnairedao.findAll();
        List<QuestionnaireDto> questionnairedtoList = new ArrayList<>();
        for (Questionnaire questionnaire : questionnaireList) {
            QuestionnaireDto questionnairedto = new QuestionnaireDto();
            MyBeanUtil.copyProperties(questionnaire, questionnairedto);
            questionnairedtoList.add(questionnairedto);
        }
        return questionnairedtoList;
    }

    public QuestionnaireDto findById(Long id) {
        Optional<Questionnaire> questionnaireOptional = questionnairedao.findById(id);
        QuestionnaireDto questionnairedto = new QuestionnaireDto();
        questionnaireOptional.ifPresent(e -> {
            MyBeanUtil.copyProperties(e, questionnairedto);
        });
        return questionnairedto;
    }

    public QuestionnaireDto findByURi(String uri) {
        Questionnaire questionnaire = new Questionnaire();
        questionnaire.setUri(uri.trim());
        ExampleMatcher matching = ExampleMatcher.matching()
        .withMatcher("uri", ExampleMatcher.GenericPropertyMatchers.exact());
        Example<Questionnaire> example = Example.of(questionnaire, matching);
        Optional<Questionnaire> questionnaireOptional = questionnairedao.findOne(example);
        QuestionnaireDto questionnairedto = new QuestionnaireDto();
        questionnaireOptional.ifPresent(e -> {
            MyBeanUtil.copyProperties(e, questionnairedto);
        });
        return questionnairedto;
    }

    @Transactional(readOnly = true)
    public Page<QuestionnaireDto> findByPage(Pageable pageable) {
        Page<Questionnaire> page = questionnairedao.findAll(pageable);
        Page<QuestionnaireDto> pageDto = page.map((e) -> {
            QuestionnaireDto questionnairedto = new QuestionnaireDto();
            MyBeanUtil.copyProperties(e, questionnairedto);
            return questionnairedto;
        });
        return pageDto;
    }

    @Transactional(readOnly = true)
    public Page<QuestionnaireDto> findByPage(QuestionnaireDto questionnairedto, Pageable pageable) {
        Questionnaire questionnaire = new Questionnaire();
        MyBeanUtil.copyProperties(questionnairedto, questionnaire);
        Specification<Questionnaire> spec = new Specification<Questionnaire>() {
            @Override
            public Predicate toPredicate(Root<Questionnaire> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = null;
                    
                        
                        
                        if (StringUtils.isNotBlank(questionnaire.getUri())) {
                            predicate = cb.or(cb.like(root.get("uri"), questionnaire.getUri()));
                        }
                        
                        
                        
                        
                        
                        
                        if (StringUtils.isNotBlank(questionnaire.getLayoutitContent())) {
                            predicate = cb.or(predicate, cb.like(root.get("layoutitContent"), questionnaire.getLayoutitContent()));
                        }
                        
                        
                    
                return predicate;
            }
        };

        Page<Questionnaire> page = questionnairedao.findAll(spec, pageable);
        Page<QuestionnaireDto> pageDto = page.map((e) -> {
            QuestionnaireDto questionnairedtoResult = new QuestionnaireDto();
            MyBeanUtil.copyProperties(e, questionnairedtoResult);
            return questionnairedtoResult;
        });
        return pageDto;
    }

    @Transactional
    public void deleteById(Long id) {
        questionnairedao.deleteById(id);
    }


}


