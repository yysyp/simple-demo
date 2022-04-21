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

    @Transactional
    public List<[(${dtoName})]> saveAll(Collection<[(${dtoName})]> [(${dtoKey})]List) {
        if (CollectionUtils.isEmpty([(${dtoKey})]List)) {
            return new ArrayList<>();
        }
        List<[(${dtoName})]> result = new ArrayList<>();
        for ([(${dtoName})] [(${dtoKey})] : [(${dtoKey})]List) {
            result.add(save([(${dtoKey})]));
        }
        return result;
    }

    public [(${dtoName})] findById(Long id) {
        Optional<[(${entityName})]> [(${entityKey})]Optional = [(${daoKey})].findById(id);
        [(${dtoName})] [(${dtoKey})] = new [(${dtoName})]();
        [(${entityKey})]Optional.ifPresent(e -> {
            MyBeanUtil.copyProperties(e, [(${dtoKey})]);
        });
        return [(${dtoKey})];
    }

    public List<[(${dtoName})]> findByAttribute(String attributeName, Object attribute) {
        Specification<[(${entityName})]> spec = new Specification<[(${entityName})]>() {
            @Override
            public Predicate toPredicate(Root<[(${entityName})]> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.and(cb.equal(root.get(attributeName), attribute));
            }
        };
        List<[(${entityName})]> [(${entityKey})]List = [(${daoKey})].findAll(spec);
        return MyBeanUtil.copyAndConvertItems([(${entityKey})]List, [(${dtoName})].class);
    }

    //@Transactional(readOnly = true)
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

    public Page<[(${dtoName})]> findInPage(Pageable pageable) {
        Page<[(${entityName})]> page = [(${daoKey})].findAll(pageable);
        Page<[(${dtoName})]> pageDto = page.map((e) -> {
            [(${dtoName})] [(${dtoKey})] = new [(${dtoName})]();
            MyBeanUtil.copyProperties(e, [(${dtoKey})]);
            return [(${dtoKey})];
        });
        return pageDto;
    }

    public List<[(${dtoName})]> findByAttributes([(${dtoName})] [(${dtoKey})], boolean orLike) {
        [(${entityName})] [(${entityKey})] = new [(${entityName})]();
        MyBeanUtil.copyProperties([(${dtoKey})], [(${entityKey})]);
        Specification<[(${entityName})]> spec = constructSpecification([(${entityKey})], orLike);
        List<[(${entityName})]> [(${entityKey})]List = [(${daoKey})].findAll(spec);
        return MyBeanUtil.copyAndConvertItems([(${entityKey})]List, [(${dtoName})].class);
    }

    public Page<[(${dtoName})]> findByAttributesInPage([(${dtoName})] [(${dtoKey})], boolean orLike, Pageable pageable) {
        [(${entityName})] [(${entityKey})] = new [(${entityName})]();
        MyBeanUtil.copyProperties([(${dtoKey})], [(${entityKey})]);
        Specification<[(${entityName})]> spec = constructSpecification([(${entityKey})], orLike);

        Page<[(${entityName})]> page = [(${daoKey})].findAll(spec, pageable);
        Page<[(${dtoName})]> pageDto = page.map((e) -> {
            [(${dtoName})] [(${dtoKey})]Result = new [(${dtoName})]();
            MyBeanUtil.copyProperties(e, [(${dtoKey})]Result);
            return [(${dtoKey})]Result;
        });
        return pageDto;
    }

    private Specification<[(${entityName})]> constructSpecification([(${entityName})] [(${entityKey})], boolean orLike) {
        Specification<[(${entityName})]> spec = new Specification<[(${entityName})]>() {
            @Override
            public Predicate toPredicate(Root<[(${entityName})]> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = null;
                if (orLike) {
                [# th:each="attr,attrStat:${entityAttrs}" ]
                    [# th:if="${attr.get('type') eq 'String'}"]
                    predicate = orLike(predicate, cb, root,"[(${attr.get('name')})]", [(${entityKey})].get[(${#strings.capitalizeWords(attr.get('name'))})]());
                    [/]
                    [# th:unless="${attr.get('type') eq 'String'}"]
                    predicate = orEqual(predicate, cb, root,"[(${attr.get('name')})]", [(${entityKey})].get[(${#strings.capitalizeWords(attr.get('name'))})]());
                    [/]
                [/]
                } else {
                [# th:each="attr,attrStat:${entityAttrs}" ]
                    predicate = andEqual(predicate, cb, root, "[(${attr.get('name')})]", [(${entityKey})].get[(${#strings.capitalizeWords(attr.get('name'))})]());
                [/]
                }

                return predicate;
            }
        };
        return spec;
    }

    private Predicate andEqual(Predicate predicate, CriteriaBuilder cb, Root<[(${entityName})]> root, String attributeName, Object attributeValue) {
        if (null == attributeValue) {
            return predicate;
        }
        if (null == predicate) {
            return cb.or(cb.equal(root.get(attributeName), attributeValue));
        } else {
            return cb.or(predicate, cb.equal(root.get(attributeName), attributeValue));
        }
    }
    private Predicate orEqual(Predicate predicate, CriteriaBuilder cb, Root<[(${entityName})]> root, String attributeName, Object attributeValue) {
        if (null == attributeValue) {
            return predicate;
        }
        if (null == predicate) {
            return cb.or(cb.equal(root.get(attributeName), attributeValue));
        } else {
            return cb.or(predicate, cb.equal(root.get(attributeName), attributeValue));
        }
    }
    private Predicate orLike(Predicate predicate, CriteriaBuilder cb, Root<[(${entityName})]> root, String attributeName, String attributeValue) {
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
        [(${daoKey})].deleteById(id);
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