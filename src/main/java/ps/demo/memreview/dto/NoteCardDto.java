

package ps.demo.memreview.dto;

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
public class NoteCardDto extends MyBaseDto {

    private String file;
    private String question;
    private String pic;
    private String answer;
    private Boolean isPic;
    private Integer reviewCount;
    private Date nextReviewDate;
    private String comments;


}


