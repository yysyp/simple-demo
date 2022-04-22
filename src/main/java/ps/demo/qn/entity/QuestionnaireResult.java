

package ps.demo.qn.entity;

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
@Table(name = "questionnaire_result")
public class QuestionnaireResult extends MyBaseEntity {

    private String uri;
    private String name;
    private String htmlFile;
    private String responseData;


}


