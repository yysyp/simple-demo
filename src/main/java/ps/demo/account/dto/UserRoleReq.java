

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
public class UserRoleReq extends MyPageReq {
    private String key;

    private Long userId;
    private Long roleId;
    private String roleName;


}





