

package ps.demo.mock.dto;

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
public class MyMockReq extends MyPageReq {
    private String key;

    private String uri;
    private Boolean regexMatch;
    private String method;
    private Integer status;
    private String headers;
    private String body;


}





