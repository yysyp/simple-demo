



package ps.demo.account.service;

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
import ps.demo.account.dto.UserRoleDto;
import ps.demo.account.dto.UserRoleReq;
import ps.demo.account.entity.UserRole;
import ps.demo.account.repository.UserRoleDao;
import ps.demo.util.MyBeanUtil;

import javax.persistence.criteria.*;

import lombok.*;

import java.util.*;
import java.math.*;

@Slf4j
@Service
public class UserRoleServiceImpl {

    @Autowired
    UserRoleDao userRoleDao;

    @Transactional
    public UserRoleDto save(UserRoleDto userRoleDto) {
        UserRole userRole = new UserRole();
        MyBeanUtil.copyProperties(userRoleDto, userRole);
        UserRole entity = userRoleDao.save(userRole);
        MyBeanUtil.copyProperties(entity, userRoleDto);
        return userRoleDto;
    }

    @Transactional
    public List<UserRoleDto> saveAll(Collection<UserRoleDto> userRoleDtoList) {
        if (CollectionUtils.isEmpty(userRoleDtoList)) {
            return new ArrayList<>();
        }
        List<UserRoleDto> result = new ArrayList<>();
        for (UserRoleDto userRoleDto : userRoleDtoList) {
            result.add(save(userRoleDto));
        }
        return result;
    }

    public UserRoleDto findById(Long id) {
        Optional<UserRole> userRoleOptional = userRoleDao.findById(id);
        UserRoleDto userRoleDto = new UserRoleDto();
        userRoleOptional.ifPresent(e -> {
            MyBeanUtil.copyProperties(e, userRoleDto);
        });
        return userRoleDto;
    }

    public List<UserRoleDto> findByAttribute(String attributeName, Object attribute) {
        Specification<UserRole> spec = new Specification<UserRole>() {
            @Override
            public Predicate toPredicate(Root<UserRole> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.and(cb.equal(root.get(attributeName), attribute));
            }
        };
        List<UserRole> userRoleList = userRoleDao.findAll(spec);
        return MyBeanUtil.copyAndConvertItems(userRoleList, UserRoleDto.class);
    }

    //@Transactional(readOnly = true)
    public List<UserRoleDto> findAll() {
        List<UserRole> userRoleList = userRoleDao.findAll();
        List<UserRoleDto> userRoleDtoList = new ArrayList<>();
        for (UserRole userRole : userRoleList) {
            UserRoleDto userRoleDto = new UserRoleDto();
            MyBeanUtil.copyProperties(userRole, userRoleDto);
            userRoleDtoList.add(userRoleDto);
        }
        return userRoleDtoList;
    }

    public Page<UserRoleDto> findInPage(Pageable pageable) {
        Page<UserRole> page = userRoleDao.findAll(pageable);
        Page<UserRoleDto> pageDto = page.map((e) -> {
            UserRoleDto userRoleDto = new UserRoleDto();
            MyBeanUtil.copyProperties(e, userRoleDto);
            return userRoleDto;
        });
        return pageDto;
    }

    public List<UserRoleDto> findByAttributes(UserRoleDto userRoleDto, boolean orLike) {
        UserRole userRole = new UserRole();
        MyBeanUtil.copyProperties(userRoleDto, userRole);
        Specification<UserRole> spec = constructSpecification(userRole, orLike);
        List<UserRole> userRoleList = userRoleDao.findAll(spec);
        return MyBeanUtil.copyAndConvertItems(userRoleList, UserRoleDto.class);
    }

    public Page<UserRoleDto> findByAttributesInPage(UserRoleDto userRoleDto, boolean orLike, Pageable pageable) {
        UserRole userRole = new UserRole();
        MyBeanUtil.copyProperties(userRoleDto, userRole);
        Specification<UserRole> spec = constructSpecification(userRole, orLike);

        Page<UserRole> page = userRoleDao.findAll(spec, pageable);
        Page<UserRoleDto> pageDto = page.map((e) -> {
            UserRoleDto userRoleDtoResult = new UserRoleDto();
            MyBeanUtil.copyProperties(e, userRoleDtoResult);
            return userRoleDtoResult;
        });
        return pageDto;
    }

    private Specification<UserRole> constructSpecification(UserRole userRole, boolean orLike) {
        Specification<UserRole> spec = new Specification<UserRole>() {
            @Override
            public Predicate toPredicate(Root<UserRole> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = null;
                if (orLike) {
                
                    
                    
                    predicate = orEqual(predicate, cb, root,"userId", userRole.getUserId());
                    
                    
                    
                    predicate = orEqual(predicate, cb, root,"roleId", userRole.getRoleId());
                    
                    
                    predicate = orLike(predicate, cb, root,"roleName", userRole.getRoleName());
                    
                    
                
                } else {
                
                    predicate = andEqual(predicate, cb, root, "userId", userRole.getUserId());
                    predicate = andEqual(predicate, cb, root, "roleId", userRole.getRoleId());
                    predicate = andEqual(predicate, cb, root, "roleName", userRole.getRoleName());
                
                }

                return predicate;
            }
        };
        return spec;
    }

    private Predicate andEqual(Predicate predicate, CriteriaBuilder cb, Root<UserRole> root, String attributeName, Object attributeValue) {
        if (null == attributeValue) {
            return predicate;
        }
        if (null == predicate) {
            return cb.or(cb.equal(root.get(attributeName), attributeValue));
        } else {
            return cb.or(predicate, cb.equal(root.get(attributeName), attributeValue));
        }
    }
    private Predicate orEqual(Predicate predicate, CriteriaBuilder cb, Root<UserRole> root, String attributeName, Object attributeValue) {
        if (null == attributeValue) {
            return predicate;
        }
        if (null == predicate) {
            return cb.or(cb.equal(root.get(attributeName), attributeValue));
        } else {
            return cb.or(predicate, cb.equal(root.get(attributeName), attributeValue));
        }
    }
    private Predicate orLike(Predicate predicate, CriteriaBuilder cb, Root<UserRole> root, String attributeName, String attributeValue) {
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
        userRoleDao.deleteById(id);
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


