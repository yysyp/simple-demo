

package ps.demo.school.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.Date;
import java.util.List;
import java.util.*;
import java.math.*;
import ps.demo.common.MyBaseDto;

//
//student
@Getter
@Setter
@ToString
public class StudentDto extends MyBaseDto {


    //0
    private String firstName;
    //1
    private String lastName;
    //2
    private Integer age;
    //3
    private BigDecimal score;
    //4
    private Date birthday;


}


