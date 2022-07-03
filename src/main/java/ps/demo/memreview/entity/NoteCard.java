

package ps.demo.memreview.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

import ps.demo.common.MyBaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.*;
import java.math.*;

import lombok.*;
import java.util.*;
import java.math.*;

@Getter
@Setter
@ToString
@Entity
@Table(name = "note_card")
public class NoteCard extends MyBaseEntity {

    private String file;
    private String question;
    private String pic;
    private String answer;
    private Boolean isPic;
    private Integer reviewCount;
    private Date nextReviewDate;
    private String comments;


}


