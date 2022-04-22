

package ps.demo.account.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ps.demo.account.dto.LoginUserDto;
import ps.demo.account.entity.LoginUser;
import ps.demo.account.entity.UserRole;
import ps.demo.account.helper.MyPrincipalUtils;
import ps.demo.account.model.LoginUserDetail;
import ps.demo.account.repository.LoginUserDao;

import ps.demo.account.repository.UserRoleDao;
import ps.demo.exception.BadRequestException;
import ps.demo.exception.CodeEnum;
import ps.demo.util.MyBeanUtil;
import ps.demo.util.MyMD5Util;
import ps.demo.util.MyStringUtil;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class LoginServiceImpl {

    @Autowired
    LoginUserDao loginUserDao;


    @Autowired
    UserRoleDao userRoleDao;


    public String givemethekey() {
        long c = loginUserDao.count();
        if (c == 0) {
            LoginUser loginUser = new LoginUser();
            loginUser.setUserName(MyPrincipalUtils.ROLE_ADMIN);
            String random = MyStringUtil.randomAlphabetic(16);
            loginUser.setPassword(MyMD5Util.getMD5(random));
            LoginUser admin = loginUserDao.save(loginUser);

            UserRole role = new UserRole();
            role.setRoleId(0L);
            role.setUserId(admin.getId());
            role.setRoleName(MyPrincipalUtils.ROLE_ADMIN);
            userRoleDao.save(role);
            UserRole role2 = new UserRole();
            role2.setRoleId(1L);
            role2.setUserId(admin.getId());
            role2.setRoleName(MyPrincipalUtils.ROLE_USER);
            userRoleDao.save(role2);

            return random;
        }
        return "";
    }

    public LoginUserDetail findUserByUserName(String username) {
        LoginUser loginUser = new LoginUser();
        loginUser.setUserName(username);
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("username", ExampleMatcher.GenericPropertyMatchers.exact())
                //.withMatcher("address" ,ExampleMatcher.GenericPropertyMatchers.contains())
                .withIgnorePaths("password");//ignore password, what ever the password value passed in will be ignored
        Example<LoginUser> example = Example.of(loginUser, matcher);
        Optional<LoginUser> loginUserOptional = loginUserDao.findOne(example);
        LoginUserDetail loginUserDetail = new LoginUserDetail();
        if (!loginUserOptional.isPresent()) {
            return null;
        }
        LoginUser loginUserResult = loginUserOptional.get();
        MyBeanUtil.copyProperties(loginUserResult, loginUserDetail);
//        userRoleDao.findAll()
        return loginUserDetail;
    }




}


