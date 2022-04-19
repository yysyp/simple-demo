

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
    QuestionnaireDao questionnaireDao;

    @Transactional
    public QuestionnaireDto save(QuestionnaireDto questionnaireDto) {
        Questionnaire questionnaire = new Questionnaire();
        MyBeanUtil.copyProperties(questionnaireDto, questionnaire);
        Questionnaire entity = questionnaireDao.save(questionnaire);
        MyBeanUtil.copyProperties(entity, questionnaireDto);
        return questionnaireDto;
    }

    @Transactional(readOnly = true)
    public List<QuestionnaireDto> findAll() {
        List<Questionnaire> questionnaireList = questionnaireDao.findAll();
        List<QuestionnaireDto> questionnaireDtoList = new ArrayList<>();
        for (Questionnaire questionnaire : questionnaireList) {
            QuestionnaireDto questionnaireDto = new QuestionnaireDto();
            MyBeanUtil.copyProperties(questionnaire, questionnaireDto);
            questionnaireDtoList.add(questionnaireDto);
        }
        return questionnaireDtoList;
    }

    public QuestionnaireDto findById(Long id) {
        Optional<Questionnaire> questionnaireOptional = questionnaireDao.findById(id);
        QuestionnaireDto questionnaireDto = new QuestionnaireDto();
        questionnaireOptional.ifPresent(e -> {
            MyBeanUtil.copyProperties(e, questionnaireDto);
        });
        return questionnaireDto;
    }

    @Transactional(readOnly = true)
    public Page<QuestionnaireDto> findByPage(Pageable pageable) {
        Page<Questionnaire> page = questionnaireDao.findAll(pageable);
        Page<QuestionnaireDto> pageDto = page.map((e) -> {
            QuestionnaireDto questionnaireDto = new QuestionnaireDto();
            MyBeanUtil.copyProperties(e, questionnaireDto);
            return questionnaireDto;
        });
        return pageDto;
    }

    @Transactional(readOnly = true)
    public Page<QuestionnaireDto> findByPage(QuestionnaireDto questionnaireDto, boolean orLike, Pageable pageable) {
        Questionnaire questionnaire = new Questionnaire();
        MyBeanUtil.copyProperties(questionnaireDto, questionnaire);
        Specification<Questionnaire> spec = constructSpecification(questionnaire, orLike);

        Page<Questionnaire> page = questionnaireDao.findAll(spec, pageable);
        Page<QuestionnaireDto> pageDto = page.map((e) -> {
            QuestionnaireDto questionnaireDtoResult = new QuestionnaireDto();
            MyBeanUtil.copyProperties(e, questionnaireDtoResult);
            return questionnaireDtoResult;
        });
        return pageDto;
    }

    private Specification<Questionnaire> constructSpecification(Questionnaire questionnaire, boolean orLike) {
        Specification<Questionnaire> spec = new Specification<Questionnaire>() {
            @Override
            public Predicate toPredicate(Root<Questionnaire> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = null;



                if (StringUtils.isNotBlank(questionnaire.getUri())) {
                    if (orLike) {
                        predicate = cb.or(cb.like(root.get("uri"), questionnaire.getUri()));
                    } else {
                        predicate = cb.and(cb.equal(root.get("uri"), questionnaire.getUri()));
                    }
                }






                if (StringUtils.isNotBlank(questionnaire.getLayoutitContent())) {
                    if (orLike) {
                        predicate = cb.or(predicate, cb.like(root.get("layoutitContent"), questionnaire.getLayoutitContent()));
                    } else {
                        predicate = cb.and(predicate, cb.equal(root.get("layoutitContent"), questionnaire.getLayoutitContent()));
                    }
                }



                return predicate;
            }
        };
        return spec;
    }

    @Transactional
    public void deleteById(Long id) {
        questionnaireDao.deleteById(id);
    }


    public QuestionnaireDto findByURi(String uri) {
        Questionnaire questionnaire = new Questionnaire();
        questionnaire.setUri(uri.trim());
        ExampleMatcher matching = ExampleMatcher.matching()
                .withMatcher("uri", ExampleMatcher.GenericPropertyMatchers.exact());
        Example<Questionnaire> example = Example.of(questionnaire, matching);
        Optional<Questionnaire> questionnaireOptional = questionnaireDao.findOne(example);
        QuestionnaireDto questionnairedto = new QuestionnaireDto();
        questionnaireOptional.ifPresent(e -> {
            MyBeanUtil.copyProperties(e, questionnairedto);
        });
        return questionnairedto;
    }
}


