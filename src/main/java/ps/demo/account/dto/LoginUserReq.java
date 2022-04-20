

package ps.demo.account.dto;

import lombok.*;
import java.util.*;
import java.math.*;

import ps.demo.common.MyPageReq;


@ToString
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class LoginUserReq extends MyPageReq {
    private String key;

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





