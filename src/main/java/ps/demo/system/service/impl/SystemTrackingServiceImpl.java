package ps.demo.system.service.impl;

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
import ps.demo.system.dto.SystemTrackingDto;
import ps.demo.system.dto.SystemTrackingReq;
import ps.demo.system.entity.SystemTracking;
import ps.demo.system.repository.SystemTrackingRepository;
import ps.demo.util.MyBeanUtil;

import javax.persistence.criteria.*;


@Slf4j
@Service
public class SystemTrackingServiceImpl {

    @Autowired
    SystemTrackingRepository systemTrackingRepository;

    @Transactional
    public SystemTrackingDto save(SystemTrackingDto systemTrackingDto) {
        SystemTracking systemTracking = new SystemTracking();
        MyBeanUtil.copyProperties(systemTrackingDto, systemTracking);
        SystemTracking entity = systemTrackingRepository.save(systemTracking);
        MyBeanUtil.copyProperties(entity, systemTrackingDto);
        return systemTrackingDto;
    }

    @Transactional(readOnly = true)
    public List<SystemTrackingDto> findAll() {
        List<SystemTracking> systemTrackingList = systemTrackingRepository.findAll();
        List<SystemTrackingDto> systemTrackingDtoList = new ArrayList<>();
        for (SystemTracking systemTracking : systemTrackingList) {
            SystemTrackingDto systemTrackingDto = new SystemTrackingDto();
            MyBeanUtil.copyProperties(systemTracking, systemTrackingDto);
            systemTrackingDtoList.add(systemTrackingDto);
        }
        return systemTrackingDtoList;
    }

    public SystemTrackingDto findById(Long id) {
        Optional<SystemTracking> systemTrackingOptional = systemTrackingRepository.findById(id);
        SystemTrackingDto systemTrackingDto = new SystemTrackingDto();
        systemTrackingOptional.ifPresent(e -> {
            MyBeanUtil.copyProperties(e, systemTrackingDto);
        });
        return systemTrackingDto;
    }

    @Transactional(readOnly = true)
    public Page<SystemTrackingDto> findByPage(Pageable pageable) {
        Page<SystemTracking> page = systemTrackingRepository.findAll(pageable);
        Page<SystemTrackingDto> pageDto = page.map((e) -> {
            SystemTrackingDto systemTrackingDto = new SystemTrackingDto();
            MyBeanUtil.copyProperties(e, systemTrackingDto);
            return systemTrackingDto;
        });
        return pageDto;
    }

    @Transactional(readOnly = true)
    public Page<SystemTrackingDto> findByPage(SystemTrackingDto systemTrackingDto, Pageable pageable) {

        SystemTracking systemTracking = new SystemTracking();
        MyBeanUtil.copyProperties(systemTrackingDto, systemTracking);

//        ExampleMatcher matching = ExampleMatcher.matching()
//        .withMatcher("countSource", ExampleMatcher.GenericPropertyMatchers.contains())
//        .withMatcher("fetchSourceByPage", ExampleMatcher.GenericPropertyMatchers.contains());
//        Example<SystemTracking> example = Example.of(systemTracking, matching);
//        Page<SystemTracking> page = systemTrackingRepository.findAll(example, pageable);

        Specification<SystemTracking> spec = new Specification<SystemTracking>() {
            @Override
            public Predicate toPredicate(Root<SystemTracking> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = null;
                if (StringUtils.isNotBlank(systemTracking.getCountSource())) {
                    predicate = cb.or(cb.like(root.get("countSource"), systemTracking.getCountSource()));
                }
                if (StringUtils.isNotBlank(systemTracking.getFetchSourceByPage())) {
                    predicate = cb.or(predicate, cb.like(root.get("fetchSourceByPage"), systemTracking.getFetchSourceByPage()));
                }
                return predicate;
                //return cb.or(cb.like(root.get("countSource"), systemTracking.getCountSource()),
                //cb.like(root.get("fetchSourceByPage"), systemTracking.getFetchSourceByPage()));

            }
        };

        Page<SystemTracking> page = systemTrackingRepository.findAll(spec, pageable);
        Page<SystemTrackingDto> pageDto = page.map((e) -> {
            SystemTrackingDto systemTrackingDtoResult = new SystemTrackingDto();
            MyBeanUtil.copyProperties(e, systemTrackingDtoResult);
            return systemTrackingDtoResult;
        });
        return pageDto;
    }

    @Transactional
    public void deleteById(Long id) {
        systemTrackingRepository.deleteById(id);
    }


}