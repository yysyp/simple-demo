

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
    QuestionnaireResponseDao questionnaireresponsedao;

    @Transactional
    public QuestionnaireResponseDto save(QuestionnaireResponseDto questionnaireresponsedto) {
        QuestionnaireResponse questionnaireresponse = new QuestionnaireResponse();
        MyBeanUtil.copyProperties(questionnaireresponsedto, questionnaireresponse);
        QuestionnaireResponse entity = questionnaireresponsedao.save(questionnaireresponse);
        MyBeanUtil.copyProperties(entity, questionnaireresponsedto);
        return questionnaireresponsedto;
    }

    @Transactional(readOnly = true)
    public List<QuestionnaireResponseDto> findAll() {
        List<QuestionnaireResponse> questionnaireresponseList = questionnaireresponsedao.findAll();
        List<QuestionnaireResponseDto> questionnaireresponsedtoList = new ArrayList<>();
        for (QuestionnaireResponse questionnaireresponse : questionnaireresponseList) {
            QuestionnaireResponseDto questionnaireresponsedto = new QuestionnaireResponseDto();
            MyBeanUtil.copyProperties(questionnaireresponse, questionnaireresponsedto);
            questionnaireresponsedtoList.add(questionnaireresponsedto);
        }
        return questionnaireresponsedtoList;
    }

    public QuestionnaireResponseDto findById(Long id) {
        Optional<QuestionnaireResponse> questionnaireresponseOptional = questionnaireresponsedao.findById(id);
        QuestionnaireResponseDto questionnaireresponsedto = new QuestionnaireResponseDto();
        questionnaireresponseOptional.ifPresent(e -> {
            MyBeanUtil.copyProperties(e, questionnaireresponsedto);
        });
        return questionnaireresponsedto;
    }

    @Transactional(readOnly = true)
    public Page<QuestionnaireResponseDto> findByPage(Pageable pageable) {
        Page<QuestionnaireResponse> page = questionnaireresponsedao.findAll(pageable);
        Page<QuestionnaireResponseDto> pageDto = page.map((e) -> {
            QuestionnaireResponseDto questionnaireresponsedto = new QuestionnaireResponseDto();
            MyBeanUtil.copyProperties(e, questionnaireresponsedto);
            return questionnaireresponsedto;
        });
        return pageDto;
    }

    @Transactional(readOnly = true)
    public Page<QuestionnaireResponseDto> findByPage(QuestionnaireResponseDto questionnaireresponsedto, Pageable pageable) {
        QuestionnaireResponse questionnaireresponse = new QuestionnaireResponse();
        MyBeanUtil.copyProperties(questionnaireresponsedto, questionnaireresponse);
        Specification<QuestionnaireResponse> spec = new Specification<QuestionnaireResponse>() {
            @Override
            public Predicate toPredicate(Root<QuestionnaireResponse> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = null;
                    
                        
                        
                        if (StringUtils.isNotBlank(questionnaireresponse.getUri())) {
                            predicate = cb.or(cb.like(root.get("uri"), questionnaireresponse.getUri()));
                        }
                        
                        
                        
                        
                        
                        
                        if (StringUtils.isNotBlank(questionnaireresponse.getResponseContent())) {
                            predicate = cb.or(predicate, cb.like(root.get("responseContent"), questionnaireresponse.getResponseContent()));
                        }
                        
                        
                    
                return predicate;
            }
        };

        Page<QuestionnaireResponse> page = questionnaireresponsedao.findAll(spec, pageable);
        Page<QuestionnaireResponseDto> pageDto = page.map((e) -> {
            QuestionnaireResponseDto questionnaireresponsedtoResult = new QuestionnaireResponseDto();
            MyBeanUtil.copyProperties(e, questionnaireresponsedtoResult);
            return questionnaireresponsedtoResult;
        });
        return pageDto;
    }

    @Transactional
    public void deleteById(Long id) {
        questionnaireresponsedao.deleteById(id);
    }


}


