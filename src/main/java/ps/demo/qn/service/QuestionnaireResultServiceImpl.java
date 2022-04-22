

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
import ps.demo.qn.dto.QuestionnaireResultDto;
import ps.demo.qn.dto.QuestionnaireResultReq;
import ps.demo.qn.entity.QuestionnaireResult;
import ps.demo.qn.repository.QuestionnaireResultDao;
import ps.demo.util.MyBeanUtil;

import javax.persistence.criteria.*;

import lombok.*;

import java.util.*;
import java.math.*;

@Slf4j
@Service
public class QuestionnaireResultServiceImpl {

    @Autowired
    QuestionnaireResultDao questionnaireResultDao;

    @Transactional
    public QuestionnaireResultDto save(QuestionnaireResultDto questionnaireResultDto) {
        QuestionnaireResult questionnaireResult = new QuestionnaireResult();
        MyBeanUtil.copyProperties(questionnaireResultDto, questionnaireResult);
        QuestionnaireResult entity = questionnaireResultDao.save(questionnaireResult);
        MyBeanUtil.copyProperties(entity, questionnaireResultDto);
        return questionnaireResultDto;
    }

    @Transactional
    public List<QuestionnaireResultDto> saveAll(Collection<QuestionnaireResultDto> questionnaireResultDtoList) {
        if (CollectionUtils.isEmpty(questionnaireResultDtoList)) {
            return new ArrayList<>();
        }
        List<QuestionnaireResultDto> result = new ArrayList<>();
        for (QuestionnaireResultDto questionnaireResultDto : questionnaireResultDtoList) {
            result.add(save(questionnaireResultDto));
        }
        return result;
    }

    public QuestionnaireResultDto findById(Long id) {
        Optional<QuestionnaireResult> questionnaireResultOptional = questionnaireResultDao.findById(id);
        QuestionnaireResultDto questionnaireResultDto = new QuestionnaireResultDto();
        questionnaireResultOptional.ifPresent(e -> {
            MyBeanUtil.copyProperties(e, questionnaireResultDto);
        });
        return questionnaireResultDto;
    }

    public List<QuestionnaireResultDto> findByAttribute(String attributeName, Object attribute) {
        Specification<QuestionnaireResult> spec = new Specification<QuestionnaireResult>() {
            @Override
            public Predicate toPredicate(Root<QuestionnaireResult> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.and(cb.equal(root.get(attributeName), attribute));
            }
        };
        List<QuestionnaireResult> questionnaireResultList = questionnaireResultDao.findAll(spec);
        return MyBeanUtil.copyAndConvertItems(questionnaireResultList, QuestionnaireResultDto.class);
    }

    //@Transactional(readOnly = true)
    public List<QuestionnaireResultDto> findAll() {
        List<QuestionnaireResult> questionnaireResultList = questionnaireResultDao.findAll();
        List<QuestionnaireResultDto> questionnaireResultDtoList = new ArrayList<>();
        for (QuestionnaireResult questionnaireResult : questionnaireResultList) {
            QuestionnaireResultDto questionnaireResultDto = new QuestionnaireResultDto();
            MyBeanUtil.copyProperties(questionnaireResult, questionnaireResultDto);
            questionnaireResultDtoList.add(questionnaireResultDto);
        }
        return questionnaireResultDtoList;
    }

    public Page<QuestionnaireResultDto> findInPage(Pageable pageable) {
        Page<QuestionnaireResult> page = questionnaireResultDao.findAll(pageable);
        Page<QuestionnaireResultDto> pageDto = page.map((e) -> {
            QuestionnaireResultDto questionnaireResultDto = new QuestionnaireResultDto();
            MyBeanUtil.copyProperties(e, questionnaireResultDto);
            return questionnaireResultDto;
        });
        return pageDto;
    }

    public List<QuestionnaireResultDto> findByAttributes(QuestionnaireResultDto questionnaireResultDto, boolean orLike) {
        QuestionnaireResult questionnaireResult = new QuestionnaireResult();
        MyBeanUtil.copyProperties(questionnaireResultDto, questionnaireResult);
        Specification<QuestionnaireResult> spec = constructSpecification(questionnaireResult, orLike);
        List<QuestionnaireResult> questionnaireResultList = questionnaireResultDao.findAll(spec);
        return MyBeanUtil.copyAndConvertItems(questionnaireResultList, QuestionnaireResultDto.class);
    }

    public Page<QuestionnaireResultDto> findByAttributesInPage(QuestionnaireResultDto questionnaireResultDto, boolean orLike, Pageable pageable) {
        QuestionnaireResult questionnaireResult = new QuestionnaireResult();
        MyBeanUtil.copyProperties(questionnaireResultDto, questionnaireResult);
        Specification<QuestionnaireResult> spec = constructSpecification(questionnaireResult, orLike);

        Page<QuestionnaireResult> page = questionnaireResultDao.findAll(spec, pageable);
        Page<QuestionnaireResultDto> pageDto = page.map((e) -> {
            QuestionnaireResultDto questionnaireResultDtoResult = new QuestionnaireResultDto();
            MyBeanUtil.copyProperties(e, questionnaireResultDtoResult);
            return questionnaireResultDtoResult;
        });
        return pageDto;
    }

    private Specification<QuestionnaireResult> constructSpecification(QuestionnaireResult questionnaireResult, boolean orLike) {
        Specification<QuestionnaireResult> spec = new Specification<QuestionnaireResult>() {
            @Override
            public Predicate toPredicate(Root<QuestionnaireResult> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = null;
                if (orLike) {
                
                    predicate = orLike(predicate, cb, root,"uri", questionnaireResult.getUri());
                    predicate = orLike(predicate, cb, root,"name", questionnaireResult.getName());
                    predicate = orLike(predicate, cb, root,"htmlFile", questionnaireResult.getHtmlFile());
                    predicate = orLike(predicate, cb, root,"responseData", questionnaireResult.getResponseData());

                } else {
                
                    predicate = andEqual(predicate, cb, root, "uri", questionnaireResult.getUri());
                    predicate = andEqual(predicate, cb, root, "name", questionnaireResult.getName());
                    predicate = andEqual(predicate, cb, root, "htmlFile", questionnaireResult.getHtmlFile());
                    predicate = andEqual(predicate, cb, root, "responseData", questionnaireResult.getResponseData());
                
                }

                return predicate;
            }
        };
        return spec;
    }

    private Predicate andEqual(Predicate predicate, CriteriaBuilder cb, Root<QuestionnaireResult> root, String attributeName, Object attributeValue) {
        if (null == attributeValue) {
            return predicate;
        }
        if (null == predicate) {
            return cb.or(cb.equal(root.get(attributeName), attributeValue));
        } else {
            return cb.or(predicate, cb.equal(root.get(attributeName), attributeValue));
        }
    }
    private Predicate orEqual(Predicate predicate, CriteriaBuilder cb, Root<QuestionnaireResult> root, String attributeName, Object attributeValue) {
        if (null == attributeValue) {
            return predicate;
        }
        if (null == predicate) {
            return cb.or(cb.equal(root.get(attributeName), attributeValue));
        } else {
            return cb.or(predicate, cb.equal(root.get(attributeName), attributeValue));
        }
    }
    private Predicate orLike(Predicate predicate, CriteriaBuilder cb, Root<QuestionnaireResult> root, String attributeName, String attributeValue) {
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
        questionnaireResultDao.deleteById(id);
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


