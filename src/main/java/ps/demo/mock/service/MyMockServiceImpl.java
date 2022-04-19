

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
    MyMockDao mymockdao;

    @Transactional
    public MyMockDto save(MyMockDto mymockdto) {
        MyMock mymock = new MyMock();
        MyBeanUtil.copyProperties(mymockdto, mymock);
        MyMock entity = mymockdao.save(mymock);
        MyBeanUtil.copyProperties(entity, mymockdto);
        return mymockdto;
    }

    @Transactional(readOnly = true)
    public List<MyMockDto> findAll() {
        List<MyMock> mymockList = mymockdao.findAll();
        List<MyMockDto> mymockdtoList = new ArrayList<>();
        for (MyMock mymock : mymockList) {
            MyMockDto mymockdto = new MyMockDto();
            MyBeanUtil.copyProperties(mymock, mymockdto);
            mymockdtoList.add(mymockdto);
        }
        return mymockdtoList;
    }

    public MyMockDto findById(Long id) {
        Optional<MyMock> mymockOptional = mymockdao.findById(id);
        MyMockDto mymockdto = new MyMockDto();
        mymockOptional.ifPresent(e -> {
            MyBeanUtil.copyProperties(e, mymockdto);
        });
        return mymockdto;
    }

    @Transactional(readOnly = true)
    public Page<MyMockDto> findByPage(Pageable pageable) {
        Page<MyMock> page = mymockdao.findAll(pageable);
        Page<MyMockDto> pageDto = page.map((e) -> {
            MyMockDto mymockdto = new MyMockDto();
            MyBeanUtil.copyProperties(e, mymockdto);
            return mymockdto;
        });
        return pageDto;
    }

    @Transactional(readOnly = true)
    public Page<MyMockDto> findByPage(MyMockDto mymockdto, Pageable pageable) {
        MyMock mymock = new MyMock();
        MyBeanUtil.copyProperties(mymockdto, mymock);
        Specification<MyMock> spec = new Specification<MyMock>() {
            @Override
            public Predicate toPredicate(Root<MyMock> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = null;
                    
                        
                        
                        if (StringUtils.isNotBlank(mymock.getUri())) {
                            predicate = cb.or(cb.like(root.get("uri"), mymock.getUri()));
                        }
                        
                        
                        
                        
                        
                        
                        
                        if (StringUtils.isNotBlank(mymock.getMethod())) {
                            predicate = cb.or(predicate, cb.like(root.get("method"), mymock.getMethod()));
                        }
                        
                        
                        
                        
                        
                        
                        if (StringUtils.isNotBlank(mymock.getHeaders())) {
                            predicate = cb.or(predicate, cb.like(root.get("headers"), mymock.getHeaders()));
                        }
                        
                        
                        
                        
                        
                        if (StringUtils.isNotBlank(mymock.getBody())) {
                            predicate = cb.or(predicate, cb.like(root.get("body"), mymock.getBody()));
                        }
                        
                        
                    
                return predicate;
            }
        };

        Page<MyMock> page = mymockdao.findAll(spec, pageable);
        Page<MyMockDto> pageDto = page.map((e) -> {
            MyMockDto mymockdtoResult = new MyMockDto();
            MyBeanUtil.copyProperties(e, mymockdtoResult);
            return mymockdtoResult;
        });
        return pageDto;
    }

    @Transactional
    public void deleteById(Long id) {
        mymockdao.deleteById(id);
    }


}


