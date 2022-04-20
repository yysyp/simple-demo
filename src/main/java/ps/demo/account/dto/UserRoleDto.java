

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
public class UserRoleDto extends MyBaseDto {

    private Long userId;
    private Long roleId;
    private String roleName;


}


