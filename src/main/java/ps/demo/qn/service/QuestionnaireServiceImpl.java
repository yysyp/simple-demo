

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

    @Transactional
    public List<QuestionnaireDto> saveAll(Collection<QuestionnaireDto> questionnaireDtoList) {
        if (CollectionUtils.isEmpty(questionnaireDtoList)) {
            return new ArrayList<>();
        }
        List<QuestionnaireDto> result = new ArrayList<>();
        for (QuestionnaireDto questionnaireDto : questionnaireDtoList) {
            result.add(save(questionnaireDto));
        }
        return result;
    }

    public QuestionnaireDto findById(Long id) {
        Optional<Questionnaire> questionnaireOptional = questionnaireDao.findById(id);
        QuestionnaireDto questionnaireDto = new QuestionnaireDto();
        questionnaireOptional.ifPresent(e -> {
            MyBeanUtil.copyProperties(e, questionnaireDto);
        });
        return questionnaireDto;
    }

    public List<QuestionnaireDto> findByAttribute(String attributeName, Object attribute) {
        Specification<Questionnaire> spec = new Specification<Questionnaire>() {
            @Override
            public Predicate toPredicate(Root<Questionnaire> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.and(cb.equal(root.get(attributeName), attribute));
            }
        };
        List<Questionnaire> questionnaireList = questionnaireDao.findAll(spec);
        return MyBeanUtil.copyAndConvertItems(questionnaireList, QuestionnaireDto.class);
    }

    //@Transactional(readOnly = true)
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

    public Page<QuestionnaireDto> findInPage(Pageable pageable) {
        Page<Questionnaire> page = questionnaireDao.findAll(pageable);
        Page<QuestionnaireDto> pageDto = page.map((e) -> {
            QuestionnaireDto questionnaireDto = new QuestionnaireDto();
            MyBeanUtil.copyProperties(e, questionnaireDto);
            return questionnaireDto;
        });
        return pageDto;
    }

    public List<QuestionnaireDto> findByAttributes(QuestionnaireDto questionnaireDto, boolean orLike) {
        Questionnaire questionnaire = new Questionnaire();
        MyBeanUtil.copyProperties(questionnaireDto, questionnaire);
        Specification<Questionnaire> spec = constructSpecification(questionnaire, orLike);
        List<Questionnaire> questionnaireList = questionnaireDao.findAll(spec);
        return MyBeanUtil.copyAndConvertItems(questionnaireList, QuestionnaireDto.class);
    }

    public Page<QuestionnaireDto> findByAttributesInPage(QuestionnaireDto questionnaireDto, boolean orLike, Pageable pageable) {
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
                if (orLike) {
                
                    predicate = orLike(predicate, cb, root,"uri", questionnaire.getUri());
                    predicate = orLike(predicate, cb, root,"name", questionnaire.getName());
                    predicate = orEqual(predicate, cb, root,"wholeHtml", questionnaire.getWholeHtml());
                    predicate = orLike(predicate, cb, root,"htmlFile", questionnaire.getHtmlFile());
                    predicate = orLike(predicate, cb, root,"htmlContent", questionnaire.getHtmlContent());

                } else {
                
                    predicate = andEqual(predicate, cb, root, "uri", questionnaire.getUri());
                    predicate = andEqual(predicate, cb, root, "name", questionnaire.getName());
                    predicate = andEqual(predicate, cb, root, "wholeHtml", questionnaire.getWholeHtml());
                    predicate = andEqual(predicate, cb, root, "htmlFile", questionnaire.getHtmlFile());
                    predicate = andEqual(predicate, cb, root, "htmlContent", questionnaire.getHtmlContent());
                
                }

                return predicate;
            }
        };
        return spec;
    }

    private Predicate andEqual(Predicate predicate, CriteriaBuilder cb, Root<Questionnaire> root, String attributeName, Object attributeValue) {
        if (null == attributeValue) {
            return predicate;
        }
        if (null == predicate) {
            return cb.or(cb.equal(root.get(attributeName), attributeValue));
        } else {
            return cb.or(predicate, cb.equal(root.get(attributeName), attributeValue));
        }
    }
    private Predicate orEqual(Predicate predicate, CriteriaBuilder cb, Root<Questionnaire> root, String attributeName, Object attributeValue) {
        if (null == attributeValue) {
            return predicate;
        }
        if (null == predicate) {
            return cb.or(cb.equal(root.get(attributeName), attributeValue));
        } else {
            return cb.or(predicate, cb.equal(root.get(attributeName), attributeValue));
        }
    }
    private Predicate orLike(Predicate predicate, CriteriaBuilder cb, Root<Questionnaire> root, String attributeName, String attributeValue) {
        if (null == attributeValue) {
            return predicate;
        }
        if (null == predicate) {
            return cb.or(cb.like(root.get(attributeName), attributeValue));
        } else {
            return cb.or(predicate, cb.like(root.get(attributeName), attributeValue));
        }
    }

    @Transactional
    public void deleteById(Long id) {
        questionnaireDao.deleteById(id);
    }

    @Transactional
    public void deleteAll(Collection<Long> idList) {
        if (CollectionUtils.isEmpty(idList)) {
            return;
        }
        for (Long id : idList) {
            deleteById(id);
        }
    }

}


