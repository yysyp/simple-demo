

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
import ps.demo.account.dto.LoginUserDto;
import ps.demo.account.dto.LoginUserReq;
import ps.demo.account.entity.LoginUser;
import ps.demo.account.repository.LoginUserDao;
import ps.demo.util.MyBeanUtil;

import javax.persistence.criteria.*;

import lombok.*;

import java.util.*;
import java.math.*;

@Slf4j
@Service
public class LoginUserServiceImpl {

    @Autowired
    LoginUserDao loginUserDao;

    @Transactional
    public LoginUserDto save(LoginUserDto loginUserDto) {
        LoginUser loginUser = new LoginUser();
        MyBeanUtil.copyProperties(loginUserDto, loginUser);
        LoginUser entity = loginUserDao.save(loginUser);
        MyBeanUtil.copyProperties(entity, loginUserDto);
        return loginUserDto;
    }

    @Transactional
    public List<LoginUserDto> saveAll(Collection<LoginUserDto> loginUserDtoList) {
        if (CollectionUtils.isEmpty(loginUserDtoList)) {
            return new ArrayList<>();
        }
        List<LoginUserDto> result = new ArrayList<>();
        for (LoginUserDto loginUserDto : loginUserDtoList) {
            result.add(save(loginUserDto));
        }
        return result;
    }

    public LoginUserDto findById(Long id) {
        Optional<LoginUser> loginUserOptional = loginUserDao.findById(id);
        LoginUserDto loginUserDto = new LoginUserDto();
        loginUserOptional.ifPresent(e -> {
            MyBeanUtil.copyProperties(e, loginUserDto);
        });
        return loginUserDto;
    }

    public List<LoginUserDto> findByAttribute(String attributeName, Object attribute) {
        Specification<LoginUser> spec = new Specification<LoginUser>() {
            @Override
            public Predicate toPredicate(Root<LoginUser> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.and(cb.equal(root.get(attributeName), attribute));
            }
        };
        List<LoginUser> loginUserList = loginUserDao.findAll(spec);
        return MyBeanUtil.copyAndConvertItems(loginUserList, LoginUserDto.class);
    }

    //@Transactional(readOnly = true)
    public List<LoginUserDto> findAll() {
        List<LoginUser> loginUserList = loginUserDao.findAll();
        List<LoginUserDto> loginUserDtoList = new ArrayList<>();
        for (LoginUser loginUser : loginUserList) {
            LoginUserDto loginUserDto = new LoginUserDto();
            MyBeanUtil.copyProperties(loginUser, loginUserDto);
            loginUserDtoList.add(loginUserDto);
        }
        return loginUserDtoList;
    }

    public Page<LoginUserDto> findInPage(Pageable pageable) {
        Page<LoginUser> page = loginUserDao.findAll(pageable);
        Page<LoginUserDto> pageDto = page.map((e) -> {
            LoginUserDto loginUserDto = new LoginUserDto();
            MyBeanUtil.copyProperties(e, loginUserDto);
            return loginUserDto;
        });
        return pageDto;
    }

    public List<LoginUserDto> findByAttributes(LoginUserDto loginUserDto, boolean orLike) {
        LoginUser loginUser = new LoginUser();
        MyBeanUtil.copyProperties(loginUserDto, loginUser);
        Specification<LoginUser> spec = constructSpecification(loginUser, orLike);
        List<LoginUser> loginUserList = loginUserDao.findAll(spec);
        return MyBeanUtil.copyAndConvertItems(loginUserList, LoginUserDto.class);
    }

    public Page<LoginUserDto> findByAttributesInPage(LoginUserDto loginUserDto, boolean orLike, Pageable pageable) {
        LoginUser loginUser = new LoginUser();
        MyBeanUtil.copyProperties(loginUserDto, loginUser);
        Specification<LoginUser> spec = constructSpecification(loginUser, orLike);

        Page<LoginUser> page = loginUserDao.findAll(spec, pageable);
        Page<LoginUserDto> pageDto = page.map((e) -> {
            LoginUserDto loginUserDtoResult = new LoginUserDto();
            MyBeanUtil.copyProperties(e, loginUserDtoResult);
            return loginUserDtoResult;
        });
        return pageDto;
    }

    private Specification<LoginUser> constructSpecification(LoginUser loginUser, boolean orLike) {
        Specification<LoginUser> spec = new Specification<LoginUser>() {
            @Override
            public Predicate toPredicate(Root<LoginUser> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = null;
                if (orLike) {
                
                    
                    predicate = orLike(predicate, cb, root,"userName", loginUser.getUserName());
                    
                    
                    
                    predicate = orLike(predicate, cb, root,"password", loginUser.getPassword());
                    
                    
                    
                    predicate = orLike(predicate, cb, root,"firstName", loginUser.getFirstName());
                    
                    
                    
                    predicate = orLike(predicate, cb, root,"lastName", loginUser.getLastName());
                    
                    
                    
                    predicate = orLike(predicate, cb, root,"sex", loginUser.getSex());
                    
                    
                    
                    predicate = orLike(predicate, cb, root,"department", loginUser.getDepartment());
                    
                    
                    
                    predicate = orLike(predicate, cb, root,"phone", loginUser.getPhone());
                    
                    
                    
                    predicate = orLike(predicate, cb, root,"email", loginUser.getEmail());
                    
                    
                    
                    predicate = orLike(predicate, cb, root,"company", loginUser.getCompany());
                    
                    
                    
                    predicate = orLike(predicate, cb, root,"salute", loginUser.getSalute());
                    
                    
                    
                    
                    predicate = orEqual(predicate, cb, root,"disabled", loginUser.getDisabled());
                    
                    
                    
                    predicate = orEqual(predicate, cb, root,"birthday", loginUser.getBirthday());
                    
                    
                    
                    predicate = orEqual(predicate, cb, root,"lastLoginTime", loginUser.getLastLoginTime());
                    
                    
                    predicate = orLike(predicate, cb, root,"lastLoginIp", loginUser.getLastLoginIp());
                    
                    
                    
                    
                    predicate = orEqual(predicate, cb, root,"failedCount", loginUser.getFailedCount());
                    
                    
                    
                    predicate = orEqual(predicate, cb, root,"version", loginUser.getVersion());
                    
                    
                    predicate = orLike(predicate, cb, root,"comments", loginUser.getComments());
                    
                    
                
                } else {
                
                    predicate = andEqual(predicate, cb, root, "userName", loginUser.getUserName());
                    predicate = andEqual(predicate, cb, root, "password", loginUser.getPassword());
                    predicate = andEqual(predicate, cb, root, "firstName", loginUser.getFirstName());
                    predicate = andEqual(predicate, cb, root, "lastName", loginUser.getLastName());
                    predicate = andEqual(predicate, cb, root, "sex", loginUser.getSex());
                    predicate = andEqual(predicate, cb, root, "department", loginUser.getDepartment());
                    predicate = andEqual(predicate, cb, root, "phone", loginUser.getPhone());
                    predicate = andEqual(predicate, cb, root, "email", loginUser.getEmail());
                    predicate = andEqual(predicate, cb, root, "company", loginUser.getCompany());
                    predicate = andEqual(predicate, cb, root, "salute", loginUser.getSalute());
                    predicate = andEqual(predicate, cb, root, "disabled", loginUser.getDisabled());
                    predicate = andEqual(predicate, cb, root, "birthday", loginUser.getBirthday());
                    predicate = andEqual(predicate, cb, root, "lastLoginTime", loginUser.getLastLoginTime());
                    predicate = andEqual(predicate, cb, root, "lastLoginIp", loginUser.getLastLoginIp());
                    predicate = andEqual(predicate, cb, root, "failedCount", loginUser.getFailedCount());
                    predicate = andEqual(predicate, cb, root, "version", loginUser.getVersion());
                    predicate = andEqual(predicate, cb, root, "comments", loginUser.getComments());
                
                }

                return predicate;
            }
        };
        return spec;
    }

    private Predicate andEqual(Predicate predicate, CriteriaBuilder cb, Root<LoginUser> root, String attributeName, Object attributeValue) {
        if (null == attributeValue) {
            return predicate;
        }
        if (null == predicate) {
            return cb.or(cb.equal(root.get(attributeName), attributeValue));
        } else {
            return cb.or(predicate, cb.equal(root.get(attributeName), attributeValue));
        }
    }
    private Predicate orEqual(Predicate predicate, CriteriaBuilder cb, Root<LoginUser> root, String attributeName, Object attributeValue) {
        if (null == attributeValue) {
            return predicate;
        }
        if (null == predicate) {
            return cb.or(cb.equal(root.get(attributeName), attributeValue));
        } else {
            return cb.or(predicate, cb.equal(root.get(attributeName), attributeValue));
        }
    }
    private Predicate orLike(Predicate predicate, CriteriaBuilder cb, Root<LoginUser> root, String attributeName, String attributeValue) {
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
        loginUserDao.deleteById(id);
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


