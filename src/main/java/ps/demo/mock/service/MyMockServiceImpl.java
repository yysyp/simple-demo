

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

    @Transactional(readOnly = true)
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

    public MyMockDto findById(Long id) {
        Optional<MyMock> myMockOptional = myMockDao.findById(id);
        MyMockDto myMockDto = new MyMockDto();
        myMockOptional.ifPresent(e -> {
            MyBeanUtil.copyProperties(e, myMockDto);
        });
        return myMockDto;
    }

    @Transactional(readOnly = true)
    public Page<MyMockDto> findByPage(Pageable pageable) {
        Page<MyMock> page = myMockDao.findAll(pageable);
        Page<MyMockDto> pageDto = page.map((e) -> {
            MyMockDto myMockDto = new MyMockDto();
            MyBeanUtil.copyProperties(e, myMockDto);
            return myMockDto;
        });
        return pageDto;
    }

    @Transactional(readOnly = true)
    public Page<MyMockDto> findByPage(MyMockDto myMockDto, boolean orLike, Pageable pageable) {
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
                
                    
                    
                    if (StringUtils.isNotBlank(myMock.getUri())) {
                        if (orLike) {
                            predicate = cb.or(cb.like(root.get("uri"), myMock.getUri()));
                        } else {
                            predicate = cb.and(cb.equal(root.get("uri"), myMock.getUri()));
                        }
                    }
                    
                    
                    
                    
                    
                    
                    
                    if (StringUtils.isNotBlank(myMock.getMethod())) {
                        if (orLike) {
                            predicate = cb.or(predicate, cb.like(root.get("method"), myMock.getMethod()));
                        } else {
                            predicate = cb.and(predicate, cb.equal(root.get("method"), myMock.getMethod()));
                        }
                    }
                    
                    
                    
                    
                    
                    
                    if (StringUtils.isNotBlank(myMock.getHeaders())) {
                        if (orLike) {
                            predicate = cb.or(predicate, cb.like(root.get("headers"), myMock.getHeaders()));
                        } else {
                            predicate = cb.and(predicate, cb.equal(root.get("headers"), myMock.getHeaders()));
                        }
                    }
                    
                    
                    
                    
                    
                    if (StringUtils.isNotBlank(myMock.getBody())) {
                        if (orLike) {
                            predicate = cb.or(predicate, cb.like(root.get("body"), myMock.getBody()));
                        } else {
                            predicate = cb.and(predicate, cb.equal(root.get("body"), myMock.getBody()));
                        }
                    }
                    
                    
                
                return predicate;
            }
        };
        return spec;
    }

    @Transactional
    public void deleteById(Long id) {
        myMockDao.deleteById(id);
    }


}


