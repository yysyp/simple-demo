

package ps.demo.company.dto;

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
public class AbcStaffDto extends MyBaseDto {

    private String firstName;
    private String lastName;
    private Integer age;
    private BigDecimal score;
    private Boolean passed;
    private Date birthday;


}


