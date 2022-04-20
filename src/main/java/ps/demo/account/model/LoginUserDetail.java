

package ps.demo.account.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ps.demo.account.dto.LoginUserDto;
import ps.demo.account.dto.UserRoleDto;
import ps.demo.common.MyBaseDto;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
public class LoginUserDetail extends LoginUserDto {

    private List<UserRoleDto> roles;

}


