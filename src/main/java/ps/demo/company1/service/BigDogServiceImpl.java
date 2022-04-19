

package ps.demo.company1.service;

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
import ps.demo.company1.dto.BigDogDto;
import ps.demo.company1.dto.BigDogReq;
import ps.demo.company1.entity.BigDog;
import ps.demo.company1.repository.BigDogDao;
import ps.demo.util.MyBeanUtil;

import javax.persistence.criteria.*;
import lombok.*;
import java.util.*;
import java.math.*;

@Slf4j
@Service
public class BigDogServiceImpl {

    @Autowired
    BigDogDao bigdogdao;

    @Transactional
    public BigDogDto save(BigDogDto bigdogdto) {
        BigDog bigdog = new BigDog();
        MyBeanUtil.copyProperties(bigdogdto, bigdog);
        BigDog entity = bigdogdao.save(bigdog);
        MyBeanUtil.copyProperties(entity, bigdogdto);
        return bigdogdto;
    }

    @Transactional(readOnly = true)
    public List<BigDogDto> findAll() {
        List<BigDog> bigdogList = bigdogdao.findAll();
        List<BigDogDto> bigdogdtoList = new ArrayList<>();
        for (BigDog bigdog : bigdogList) {
            BigDogDto bigdogdto = new BigDogDto();
            MyBeanUtil.copyProperties(bigdog, bigdogdto);
            bigdogdtoList.add(bigdogdto);
        }
        return bigdogdtoList;
    }

    public BigDogDto findById(Long id) {
        Optional<BigDog> bigdogOptional = bigdogdao.findById(id);
        BigDogDto bigdogdto = new BigDogDto();
        bigdogOptional.ifPresent(e -> {
            MyBeanUtil.copyProperties(e, bigdogdto);
        });
        return bigdogdto;
    }

    @Transactional(readOnly = true)
    public Page<BigDogDto> findByPage(Pageable pageable) {
        Page<BigDog> page = bigdogdao.findAll(pageable);
        Page<BigDogDto> pageDto = page.map((e) -> {
            BigDogDto bigdogdto = new BigDogDto();
            MyBeanUtil.copyProperties(e, bigdogdto);
            return bigdogdto;
        });
        return pageDto;
    }

    @Transactional(readOnly = true)
    public Page<BigDogDto> findByPage(BigDogDto bigdogdto, boolean orLike, Pageable pageable) {
        BigDog bigdog = new BigDog();
        MyBeanUtil.copyProperties(bigdogdto, bigdog);
        Specification<BigDog> spec = constructSpecification(bigdog, orLike);

        Page<BigDog> page = bigdogdao.findAll(spec, pageable);
        Page<BigDogDto> pageDto = page.map((e) -> {
            BigDogDto bigdogdtoResult = new BigDogDto();
            MyBeanUtil.copyProperties(e, bigdogdtoResult);
            return bigdogdtoResult;
        });
        return pageDto;
    }

    private Specification<BigDog> constructSpecification(BigDog bigdog, boolean orLike) {
        Specification<BigDog> spec = new Specification<BigDog>() {
            @Override
            public Predicate toPredicate(Root<BigDog> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = null;
                
                    
                    
                    if (StringUtils.isNotBlank(bigdog.getFirstName())) {
                        if (orLike) {
                            predicate = cb.or(cb.like(root.get("firstName"), bigdog.getFirstName()));
                        } else {
                            predicate = cb.and(cb.equal(root.get("firstName"), bigdog.getFirstName()));
                        }
                    }
                    
                    
                    
                    
                    
                    
                    if (StringUtils.isNotBlank(bigdog.getLastName())) {
                        if (orLike) {
                            predicate = cb.or(predicate, cb.like(root.get("lastName"), bigdog.getLastName()));
                        } else {
                            predicate = cb.and(predicate, cb.equal(root.get("lastName"), bigdog.getLastName()));
                        }
                    }
                    
                    
                    
                    
                    
                    
                    
                    
                    if (StringUtils.isNotBlank(bigdog.getComments())) {
                        if (orLike) {
                            predicate = cb.or(predicate, cb.like(root.get("comments"), bigdog.getComments()));
                        } else {
                            predicate = cb.and(predicate, cb.equal(root.get("comments"), bigdog.getComments()));
                        }
                    }
                    
                    
                    
                
                return predicate;
            }
        };
        return spec;
    }

    @Transactional
    public void deleteById(Long id) {
        bigdogdao.deleteById(id);
    }


}


