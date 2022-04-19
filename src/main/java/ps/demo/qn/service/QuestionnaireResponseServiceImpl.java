

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
import ps.demo.qn.dto.QuestionnaireResponseDto;
import ps.demo.qn.dto.QuestionnaireResponseReq;
import ps.demo.qn.entity.QuestionnaireResponse;
import ps.demo.qn.repository.QuestionnaireResponseDao;
import ps.demo.util.MyBeanUtil;

import javax.persistence.criteria.*;
import lombok.*;
import java.util.*;
import java.math.*;

@Slf4j
@Service
public class QuestionnaireResponseServiceImpl {

    @Autowired
    QuestionnaireResponseDao questionnaireResponseDao;

    @Transactional
    public QuestionnaireResponseDto save(QuestionnaireResponseDto questionnaireResponseDto) {
        QuestionnaireResponse questionnaireResponse = new QuestionnaireResponse();
        MyBeanUtil.copyProperties(questionnaireResponseDto, questionnaireResponse);
        QuestionnaireResponse entity = questionnaireResponseDao.save(questionnaireResponse);
        MyBeanUtil.copyProperties(entity, questionnaireResponseDto);
        return questionnaireResponseDto;
    }

    @Transactional(readOnly = true)
    public List<QuestionnaireResponseDto> findAll() {
        List<QuestionnaireResponse> questionnaireResponseList = questionnaireResponseDao.findAll();
        List<QuestionnaireResponseDto> questionnaireResponseDtoList = new ArrayList<>();
        for (QuestionnaireResponse questionnaireResponse : questionnaireResponseList) {
            QuestionnaireResponseDto questionnaireResponseDto = new QuestionnaireResponseDto();
            MyBeanUtil.copyProperties(questionnaireResponse, questionnaireResponseDto);
            questionnaireResponseDtoList.add(questionnaireResponseDto);
        }
        return questionnaireResponseDtoList;
    }

    public QuestionnaireResponseDto findById(Long id) {
        Optional<QuestionnaireResponse> questionnaireResponseOptional = questionnaireResponseDao.findById(id);
        QuestionnaireResponseDto questionnaireResponseDto = new QuestionnaireResponseDto();
        questionnaireResponseOptional.ifPresent(e -> {
            MyBeanUtil.copyProperties(e, questionnaireResponseDto);
        });
        return questionnaireResponseDto;
    }

    @Transactional(readOnly = true)
    public Page<QuestionnaireResponseDto> findByPage(Pageable pageable) {
        Page<QuestionnaireResponse> page = questionnaireResponseDao.findAll(pageable);
        Page<QuestionnaireResponseDto> pageDto = page.map((e) -> {
            QuestionnaireResponseDto questionnaireResponseDto = new QuestionnaireResponseDto();
            MyBeanUtil.copyProperties(e, questionnaireResponseDto);
            return questionnaireResponseDto;
        });
        return pageDto;
    }

    @Transactional(readOnly = true)
    public Page<QuestionnaireResponseDto> findByPage(QuestionnaireResponseDto questionnaireResponseDto, boolean orLike, Pageable pageable) {
        QuestionnaireResponse questionnaireResponse = new QuestionnaireResponse();
        MyBeanUtil.copyProperties(questionnaireResponseDto, questionnaireResponse);
        Specification<QuestionnaireResponse> spec = constructSpecification(questionnaireResponse, orLike);

        Page<QuestionnaireResponse> page = questionnaireResponseDao.findAll(spec, pageable);
        Page<QuestionnaireResponseDto> pageDto = page.map((e) -> {
            QuestionnaireResponseDto questionnaireResponseDtoResult = new QuestionnaireResponseDto();
            MyBeanUtil.copyProperties(e, questionnaireResponseDtoResult);
            return questionnaireResponseDtoResult;
        });
        return pageDto;
    }

    private Specification<QuestionnaireResponse> constructSpecification(QuestionnaireResponse questionnaireResponse, boolean orLike) {
        Specification<QuestionnaireResponse> spec = new Specification<QuestionnaireResponse>() {
            @Override
            public Predicate toPredicate(Root<QuestionnaireResponse> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = null;
                
                    
                    
                    if (StringUtils.isNotBlank(questionnaireResponse.getUri())) {
                        if (orLike) {
                            predicate = cb.or(cb.like(root.get("uri"), questionnaireResponse.getUri()));
                        } else {
                            predicate = cb.and(cb.equal(root.get("uri"), questionnaireResponse.getUri()));
                        }
                    }
                    
                    
                    
                    
                    
                    
                    if (StringUtils.isNotBlank(questionnaireResponse.getResponseContent())) {
                        if (orLike) {
                            predicate = cb.or(predicate, cb.like(root.get("responseContent"), questionnaireResponse.getResponseContent()));
                        } else {
                            predicate = cb.and(predicate, cb.equal(root.get("responseContent"), questionnaireResponse.getResponseContent()));
                        }
                    }
                    
                    
                
                return predicate;
            }
        };
        return spec;
    }

    @Transactional
    public void deleteById(Long id) {
        questionnaireResponseDao.deleteById(id);
    }


}


