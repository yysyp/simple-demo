

package ps.demo.account.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.Date;
import java.util.List;
import java.util.*;
import java.math.*;
import ps.demo.common.MyBaseDto;
import lombok.*;
import java.util.*;
import java.math.*;
@Getter
@Setter
@ToString
public class LoginUserDto extends MyBaseDto {

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


