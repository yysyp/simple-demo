

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
public class QuestionnaireResultReq extends MyPageReq {
    private String key;

    private String uri;
    private String name;
    private String htmlFile;
    private String responseData;


}





