

package ps.demo.company.service;

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
import ps.demo.company.dto.AbcStaffDto;
import ps.demo.company.dto.AbcStaffReq;
import ps.demo.company.entity.AbcStaff;
import ps.demo.company.repository.AbcStaffDao;
import ps.demo.util.MyBeanUtil;

import javax.persistence.criteria.*;
import lombok.*;
import java.util.*;
import java.math.*;

@Slf4j
@Service
public class AbcStaffServiceImpl {

    @Autowired
    AbcStaffDao abcstaffdao;

    @Transactional
    public AbcStaffDto save(AbcStaffDto abcstaffdto) {
        AbcStaff abcstaff = new AbcStaff();
        MyBeanUtil.copyProperties(abcstaffdto, abcstaff);
        AbcStaff entity = abcstaffdao.save(abcstaff);
        MyBeanUtil.copyProperties(entity, abcstaffdto);
        return abcstaffdto;
    }

    @Transactional(readOnly = true)
    public List<AbcStaffDto> findAll() {
        List<AbcStaff> abcstaffList = abcstaffdao.findAll();
        List<AbcStaffDto> abcstaffdtoList = new ArrayList<>();
        for (AbcStaff abcstaff : abcstaffList) {
            AbcStaffDto abcstaffdto = new AbcStaffDto();
            MyBeanUtil.copyProperties(abcstaff, abcstaffdto);
            abcstaffdtoList.add(abcstaffdto);
        }
        return abcstaffdtoList;
    }

    public AbcStaffDto findById(Long id) {
        Optional<AbcStaff> abcstaffOptional = abcstaffdao.findById(id);
        AbcStaffDto abcstaffdto = new AbcStaffDto();
        abcstaffOptional.ifPresent(e -> {
            MyBeanUtil.copyProperties(e, abcstaffdto);
        });
        return abcstaffdto;
    }

    @Transactional(readOnly = true)
    public Page<AbcStaffDto> findByPage(Pageable pageable) {
        Page<AbcStaff> page = abcstaffdao.findAll(pageable);
        Page<AbcStaffDto> pageDto = page.map((e) -> {
            AbcStaffDto abcstaffdto = new AbcStaffDto();
            MyBeanUtil.copyProperties(e, abcstaffdto);
            return abcstaffdto;
        });
        return pageDto;
    }

    @Transactional(readOnly = true)
    public Page<AbcStaffDto> findByPage(AbcStaffDto abcstaffdto, Pageable pageable) {
        AbcStaff abcstaff = new AbcStaff();
        MyBeanUtil.copyProperties(abcstaffdto, abcstaff);
        Specification<AbcStaff> spec = new Specification<AbcStaff>() {
            @Override
            public Predicate toPredicate(Root<AbcStaff> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = null;
                    
                        
                        
                        if (StringUtils.isNotBlank(abcstaff.getFirstName())) {
                            predicate = cb.or(cb.like(root.get("firstName"), abcstaff.getFirstName()));
                        }
                        
                        
                        
                        
                        
                        
                        if (StringUtils.isNotBlank(abcstaff.getLastName())) {
                            predicate = cb.or(predicate, cb.like(root.get("lastName"), abcstaff.getLastName()));
                        }
                        
                        
                        
                        
                        
                        
                    
                return predicate;
            }
        };

        Page<AbcStaff> page = abcstaffdao.findAll(spec, pageable);
        Page<AbcStaffDto> pageDto = page.map((e) -> {
            AbcStaffDto abcstaffdtoResult = new AbcStaffDto();
            MyBeanUtil.copyProperties(e, abcstaffdtoResult);
            return abcstaffdtoResult;
        });
        return pageDto;
    }

    @Transactional
    public void deleteById(Long id) {
        abcstaffdao.deleteById(id);
    }


}


