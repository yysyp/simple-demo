

package ps.demo.qn.dto;

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
public class QuestionnaireReq extends MyPageReq {
    private String key;

    private String uri;
    private String layoutitContent;


}





