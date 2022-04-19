

package ps.demo.comp2.service;

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
import ps.demo.comp2.dto.SmallCatMiaoDto;
import ps.demo.comp2.dto.SmallCatMiaoReq;
import ps.demo.comp2.entity.SmallCatMiao;
import ps.demo.comp2.repository.SmallCatMiaoDao;
import ps.demo.util.MyBeanUtil;

import javax.persistence.criteria.*;
import lombok.*;
import java.util.*;
import java.math.*;

@Slf4j
@Service
public class SmallCatMiaoServiceImpl {

    @Autowired
    SmallCatMiaoDao smallcatmiaodao;

    @Transactional
    public SmallCatMiaoDto save(SmallCatMiaoDto smallcatmiaodto) {
        SmallCatMiao smallcatmiao = new SmallCatMiao();
        MyBeanUtil.copyProperties(smallcatmiaodto, smallcatmiao);
        SmallCatMiao entity = smallcatmiaodao.save(smallcatmiao);
        MyBeanUtil.copyProperties(entity, smallcatmiaodto);
        return smallcatmiaodto;
    }

    @Transactional(readOnly = true)
    public List<SmallCatMiaoDto> findAll() {
        List<SmallCatMiao> smallcatmiaoList = smallcatmiaodao.findAll();
        List<SmallCatMiaoDto> smallcatmiaodtoList = new ArrayList<>();
        for (SmallCatMiao smallcatmiao : smallcatmiaoList) {
            SmallCatMiaoDto smallcatmiaodto = new SmallCatMiaoDto();
            MyBeanUtil.copyProperties(smallcatmiao, smallcatmiaodto);
            smallcatmiaodtoList.add(smallcatmiaodto);
        }
        return smallcatmiaodtoList;
    }

    public SmallCatMiaoDto findById(Long id) {
        Optional<SmallCatMiao> smallcatmiaoOptional = smallcatmiaodao.findById(id);
        SmallCatMiaoDto smallcatmiaodto = new SmallCatMiaoDto();
        smallcatmiaoOptional.ifPresent(e -> {
            MyBeanUtil.copyProperties(e, smallcatmiaodto);
        });
        return smallcatmiaodto;
    }

    @Transactional(readOnly = true)
    public Page<SmallCatMiaoDto> findByPage(Pageable pageable) {
        Page<SmallCatMiao> page = smallcatmiaodao.findAll(pageable);
        Page<SmallCatMiaoDto> pageDto = page.map((e) -> {
            SmallCatMiaoDto smallcatmiaodto = new SmallCatMiaoDto();
            MyBeanUtil.copyProperties(e, smallcatmiaodto);
            return smallcatmiaodto;
        });
        return pageDto;
    }

    @Transactional(readOnly = true)
    public Page<SmallCatMiaoDto> findByPage(SmallCatMiaoDto smallcatmiaodto, boolean orLike, Pageable pageable) {
        SmallCatMiao smallcatmiao = new SmallCatMiao();
        MyBeanUtil.copyProperties(smallcatmiaodto, smallcatmiao);
        Specification<SmallCatMiao> spec = constructSpecification(smallcatmiao, orLike);

        Page<SmallCatMiao> page = smallcatmiaodao.findAll(spec, pageable);
        Page<SmallCatMiaoDto> pageDto = page.map((e) -> {
            SmallCatMiaoDto smallcatmiaodtoResult = new SmallCatMiaoDto();
            MyBeanUtil.copyProperties(e, smallcatmiaodtoResult);
            return smallcatmiaodtoResult;
        });
        return pageDto;
    }

    private Specification<SmallCatMiao> constructSpecification(SmallCatMiao smallcatmiao, boolean orLike) {
        Specification<SmallCatMiao> spec = new Specification<SmallCatMiao>() {
            @Override
            public Predicate toPredicate(Root<SmallCatMiao> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = null;
                
                    
                    
                    if (StringUtils.isNotBlank(smallcatmiao.getFirstName())) {
                        if (orLike) {
                            predicate = cb.or(cb.like(root.get("firstName"), smallcatmiao.getFirstName()));
                        } else {
                            predicate = cb.and(cb.equal(root.get("firstName"), smallcatmiao.getFirstName()));
                        }
                    }
                    
                    
                    
                    
                    
                    
                    if (StringUtils.isNotBlank(smallcatmiao.getLastName())) {
                        if (orLike) {
                            predicate = cb.or(predicate, cb.like(root.get("lastName"), smallcatmiao.getLastName()));
                        } else {
                            predicate = cb.and(predicate, cb.equal(root.get("lastName"), smallcatmiao.getLastName()));
                        }
                    }
                    
                    
                    
                    
                    
                    if (StringUtils.isNotBlank(smallcatmiao.getLastName2())) {
                        if (orLike) {
                            predicate = cb.or(predicate, cb.like(root.get("lastName2"), smallcatmiao.getLastName2()));
                        } else {
                            predicate = cb.and(predicate, cb.equal(root.get("lastName2"), smallcatmiao.getLastName2()));
                        }
                    }
                    
                    
                    
                    
                    
                    
                    
                    
                    if (StringUtils.isNotBlank(smallcatmiao.getAddr())) {
                        if (orLike) {
                            predicate = cb.or(predicate, cb.like(root.get("addr"), smallcatmiao.getAddr()));
                        } else {
                            predicate = cb.and(predicate, cb.equal(root.get("addr"), smallcatmiao.getAddr()));
                        }
                    }
                    
                    
                    
                    
                    
                    
                    
                    if (StringUtils.isNotBlank(smallcatmiao.getComments())) {
                        if (orLike) {
                            predicate = cb.or(predicate, cb.like(root.get("comments"), smallcatmiao.getComments()));
                        } else {
                            predicate = cb.and(predicate, cb.equal(root.get("comments"), smallcatmiao.getComments()));
                        }
                    }
                    
                    
                    
                    
                
                return predicate;
            }
        };
        return spec;
    }

    @Transactional
    public void deleteById(Long id) {
        smallcatmiaodao.deleteById(id);
    }


}


