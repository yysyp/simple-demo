

package ps.demo.mock.dto;

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
public class MyMockDto extends MyBaseDto {

    private String uri;
    private Boolean regexMatch;
    private String method;
    private Integer status;
    private String headers;
    private String body;


}


