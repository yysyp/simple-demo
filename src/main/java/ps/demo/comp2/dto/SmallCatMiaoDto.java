

package ps.demo.comp2.dto;

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
public class SmallCatMiaoDto extends MyBaseDto {

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


