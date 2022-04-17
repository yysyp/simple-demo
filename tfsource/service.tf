package [(${packageName}+'.'+${moduleName}+'.'+${serviceFolder})];

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
import [(${packageName}+'.'+${moduleName}+'.'+${dtoFolder})].[(${dtoName})];
import [(${packageName}+'.'+${moduleName}+'.'+${dtoFolder})].[(${reqName})];
import [(${packageName}+'.'+${moduleName}+'.'+${entityFolder})].[(${entityName})];
import [(${packageName}+'.'+${moduleName}+'.'+${daoFolder})].[(${daoName})];
import ps.demo.util.MyBeanUtil;

import javax.persistence.criteria.*;
import lombok.*;
import java.util.*;
import java.math.*;

@Slf4j
@Service
public class [(${serviceName})] {

    @Autowired
    [(${daoName})] [(${daoKey})];

    @Transactional
    public [(${dtoName})] save([(${dtoName})] [(${dtoKey})]) {
        [(${entityName})] [(${entityKey})] = new [(${entityName})]();
        MyBeanUtil.copyProperties([(${dtoKey})], [(${entityKey})]);
        [(${entityName})] entity = [(${daoKey})].save([(${entityKey})]);
        MyBeanUtil.copyProperties(entity, [(${dtoKey})]);
        return [(${dtoKey})];
    }

    @Transactional(readOnly = true)
    public List<[(${dtoName})]> findAll() {
        List<[(${entityName})]> [(${entityKey})]List = [(${daoKey})].findAll();
        List<[(${dtoName})]> [(${dtoKey})]List = new ArrayList<>();
        for ([(${entityName})] [(${entityKey})] : [(${entityKey})]List) {
            [(${dtoName})] [(${dtoKey})] = new [(${dtoName})]();
            MyBeanUtil.copyProperties([(${entityKey})], [(${dtoKey})]);
            [(${dtoKey})]List.add([(${dtoKey})]);
        }
        return [(${dtoKey})]List;
    }

    public [(${dtoName})] findById(Long id) {
        Optional<[(${entityName})]> [(${entityKey})]Optional = [(${daoKey})].findById(id);
        [(${dtoName})] [(${dtoKey})] = new [(${dtoName})]();
        [(${entityKey})]Optional.ifPresent(e -> {
            MyBeanUtil.copyProperties(e, [(${dtoKey})]);
        });
        return [(${dtoKey})];
    }

    @Transactional(readOnly = true)
    public Page<[(${dtoName})]> findByPage(Pageable pageable) {
        Page<[(${entityName})]> page = [(${daoKey})].findAll(pageable);
        Page<[(${dtoName})]> pageDto = page.map((e) -> {
            [(${dtoName})] [(${dtoKey})] = new [(${dtoName})]();
            MyBeanUtil.copyProperties(e, [(${dtoKey})]);
            return [(${dtoKey})];
        });
        return pageDto;
    }

    @Transactional(readOnly = true)
    public Page<[(${dtoName})]> findByPage([(${dtoName})] [(${dtoKey})], Pageable pageable) {
        [(${entityName})] [(${entityKey})] = new [(${entityName})]();
        MyBeanUtil.copyProperties([(${dtoKey})], [(${entityKey})]);
        Specification<[(${entityName})]> spec = new Specification<[(${entityName})]>() {
            @Override
            public Predicate toPredicate(Root<[(${entityName})]> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = null;
                    [# th:each="attr,attrStat:${entityAttrs}" ]
                        [# th:if="${attr.get('type') eq 'String'}"]
                        [# th:if="${attrStat.index eq 0}"]
                        if (StringUtils.isNotBlank([(${entityKey})].get[(${#strings.capitalizeWords(attr.get('name'))})]())) {
                            predicate = cb.or(cb.like(root.get("[(${attr.get('name')})]"), [(${entityKey})].get[(${#strings.capitalizeWords(attr.get('name'))})]()));
                        }
                        [/]
                        [# th:if="${attrStat.index neq 0}"]
                        if (StringUtils.isNotBlank([(${entityKey})].get[(${#strings.capitalizeWords(attr.get('name'))})]())) {
                            predicate = cb.or(predicate, cb.like(root.get("[(${attr.get('name')})]"), [(${entityKey})].get[(${#strings.capitalizeWords(attr.get('name'))})]()));
                        }
                        [/]
                        [/]
                    [/]
                return predicate;
            }
        };

        Page<[(${entityName})]> page = [(${daoKey})].findAll(spec, pageable);
        Page<[(${dtoName})]> pageDto = page.map((e) -> {
            [(${dtoName})] [(${dtoKey})]Result = new [(${dtoName})]();
            MyBeanUtil.copyProperties(e, [(${dtoKey})]Result);
            return [(${dtoKey})]Result;
        });
        return pageDto;
    }

    @Transactional
    public void deleteById(Long id) {
        [(${daoKey})].deleteById(id);
    }


}