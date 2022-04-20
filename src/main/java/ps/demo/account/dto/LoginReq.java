package ps.demo.account.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LoginReq implements java.io.Serializable {

    private String username;
    private String password;

}
