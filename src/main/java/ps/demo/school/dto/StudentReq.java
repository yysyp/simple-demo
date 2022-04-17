

package ps.demo.school.dto;

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
public class StudentReq extends MyPageReq {
    private String key;

    private String firstName;
    private String lastName;
    private Integer age;
    private BigDecimal score;
    private Date birthday;


}





