

package ps.demo.account.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

import ps.demo.common.MyBaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.*;
import java.math.*;

import lombok.*;
import java.util.*;
import java.math.*;

@Getter
@Setter
@ToString
@Entity
@Table(name = "login_user")
public class LoginUser extends MyBaseEntity {

    private String userName;
    private String password;
    private String firstName;
    private String lastName;
    private String sex;
    private String department;
    private String phone;
    private String email;
    private String company;
    private String salute;
    private Boolean disabled;
    private Date birthday;
    private Date lastLoginTime;
    private String lastLoginIp;
    private Integer failedCount;
    private Long version;
    private String comments;


}


