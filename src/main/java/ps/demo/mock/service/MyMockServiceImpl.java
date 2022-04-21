

package ps.demo.mock.service;

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
import ps.demo.mock.dto.MyMockDto;
import ps.demo.mock.dto.MyMockReq;
import ps.demo.mock.entity.MyMock;
import ps.demo.mock.repository.MyMockDao;
import ps.demo.util.MyBeanUtil;

import javax.persistence.criteria.*;

import lombok.*;

import java.util.*;
import java.math.*;

@Slf4j
@Service
public class MyMockServiceImpl {

    @Autowired
    MyMockDao myMockDao;

    @Transactional
    public MyMockDto save(MyMockDto myMockDto) {
        MyMock myMock = new MyMock();
        MyBeanUtil.copyProperties(myMockDto, myMock);
        MyMock entity = myMockDao.save(myMock);
        MyBeanUtil.copyProperties(entity, myMockDto);
        return myMockDto;
    }

    @Transactional
    public List<MyMockDto> saveAll(Collection<MyMockDto> myMockDtoList) {
        if (CollectionUtils.isEmpty(myMockDtoList)) {
            return new ArrayList<>();
        }
        List<MyMockDto> result = new ArrayList<>();
        for (MyMockDto myMockDto : myMockDtoList) {
            result.add(save(myMockDto));
        }
        return result;
    }

    public MyMockDto findById(Long id) {
        Optional<MyMock> myMockOptional = myMockDao.findById(id);
        MyMockDto myMockDto = new MyMockDto();
        myMockOptional.ifPresent(e -> {
            MyBeanUtil.copyProperties(e, myMockDto);
        });
        return myMockDto;
    }

    public List<MyMockDto> findByAttribute(String attributeName, Object attribute) {
        Specification<MyMock> spec = new Specification<MyMock>() {
            @Override
            public Predicate toPredicate(Root<MyMock> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.and(cb.equal(root.get(attributeName), attribute));
            }
        };
        List<MyMock> myMockList = myMockDao.findAll(spec);
        return MyBeanUtil.copyAndConvertItems(myMockList, MyMockDto.class);
    }

    //@Transactional(readOnly = true)
    public List<MyMockDto> findAll() {
        List<MyMock> myMockList = myMockDao.findAll();
        List<MyMockDto> myMockDtoList = new ArrayList<>();
        for (MyMock myMock : myMockList) {
            MyMockDto myMockDto = new MyMockDto();
            MyBeanUtil.copyProperties(myMock, myMockDto);
            myMockDtoList.add(myMockDto);
        }
        return myMockDtoList;
    }

    public Page<MyMockDto> findInPage(Pageable pageable) {
        Page<MyMock> page = myMockDao.findAll(pageable);
        Page<MyMockDto> pageDto = page.map((e) -> {
            MyMockDto myMockDto = new MyMockDto();
            MyBeanUtil.copyProperties(e, myMockDto);
            return myMockDto;
        });
        return pageDto;
    }

    public List<MyMockDto> findByAttributes(MyMockDto myMockDto, boolean orLike) {
        MyMock myMock = new MyMock();
        MyBeanUtil.copyProperties(myMockDto, myMock);
        Specification<MyMock> spec = constructSpecification(myMock, orLike);
        List<MyMock> myMockList = myMockDao.findAll(spec);
        return MyBeanUtil.copyAndConvertItems(myMockList, MyMockDto.class);
    }

    public Page<MyMockDto> findByAttributesInPage(MyMockDto myMockDto, boolean orLike, Pageable pageable) {
        MyMock myMock = new MyMock();
        MyBeanUtil.copyProperties(myMockDto, myMock);
        Specification<MyMock> spec = constructSpecification(myMock, orLike);

        Page<MyMock> page = myMockDao.findAll(spec, pageable);
        Page<MyMockDto> pageDto = page.map((e) -> {
            MyMockDto myMockDtoResult = new MyMockDto();
            MyBeanUtil.copyProperties(e, myMockDtoResult);
            return myMockDtoResult;
        });
        return pageDto;
    }

    private Specification<MyMock> constructSpecification(MyMock myMock, boolean orLike) {
        Specification<MyMock> spec = new Specification<MyMock>() {
            @Override
            public Predicate toPredicate(Root<MyMock> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = null;
                if (orLike) {
                
                    predicate = orLike(predicate, cb, root,"uri", myMock.getUri());
                    predicate = orEqual(predicate, cb, root,"regexMatch", myMock.getRegexMatch());
                    predicate = orLike(predicate, cb, root,"method", myMock.getMethod());
                    predicate = orEqual(predicate, cb, root,"status", myMock.getStatus());
                    predicate = orLike(predicate, cb, root,"headers", myMock.getHeaders());
                    predicate = orLike(predicate, cb, root,"body", myMock.getBody());

                } else {
                
                    predicate = andEqual(predicate, cb, root, "uri", myMock.getUri());
                    predicate = andEqual(predicate, cb, root, "regexMatch", myMock.getRegexMatch());
                    predicate = andEqual(predicate, cb, root, "method", myMock.getMethod());
                    predicate = andEqual(predicate, cb, root, "status", myMock.getStatus());
                    predicate = andEqual(predicate, cb, root, "headers", myMock.getHeaders());
                    predicate = andEqual(predicate, cb, root, "body", myMock.getBody());
                
                }

                return predicate;
            }
        };
        return spec;
    }

    private Predicate andEqual(Predicate predicate, CriteriaBuilder cb, Root<MyMock> root, String attributeName, Object attributeValue) {
        if (null == attributeValue) {
            return predicate;
        }
        if (null == predicate) {
            return cb.or(cb.equal(root.get(attributeName), attributeValue));
        } else {
            return cb.or(predicate, cb.equal(root.get(attributeName), attributeValue));
        }
    }
    private Predicate orEqual(Predicate predicate, CriteriaBuilder cb, Root<MyMock> root, String attributeName, Object attributeValue) {
        if (null == attributeValue) {
            return predicate;
        }
        if (null == predicate) {
            return cb.or(cb.equal(root.get(attributeName), attributeValue));
        } else {
            return cb.or(predicate, cb.equal(root.get(attributeName), attributeValue));
        }
    }
    private Predicate orLike(Predicate predicate, CriteriaBuilder cb, Root<MyMock> root, String attributeName, String attributeValue) {
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
        myMockDao.deleteById(id);
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


