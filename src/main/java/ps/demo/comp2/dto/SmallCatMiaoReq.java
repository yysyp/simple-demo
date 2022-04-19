

package ps.demo.comp2.dto;

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
public class SmallCatMiaoReq extends MyPageReq {
    private String key;

    private String firstName;
    private String lastName;
    private String lastName2;
    private Integer age;
    private BigDecimal score1;
    private BigDecimal score2;
    private String addr;
    private Integer seat;
    private Boolean passed;
    private String comments;
    private Boolean passed2;
    private Date birthday;


}





