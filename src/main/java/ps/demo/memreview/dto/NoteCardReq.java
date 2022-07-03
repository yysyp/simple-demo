

package ps.demo.memreview.dto;

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
public class NoteCardReq extends MyPageReq {
    private String key;

    private String file;
    private String question;
    private String pic;
    private String answer;
    private Boolean isPic;
    private Integer reviewCount;
    private Date nextReviewDate;
    private String comments;


}





