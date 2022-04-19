

package ps.demo.comp2.entity;

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
@Table(name = "small_cat_miao")
public class SmallCatMiao extends MyBaseEntity {

    private String firstName;
    private String lastName;
    private String lastName2;
    private Integer age;
    private BigDecimal score1;
    private BigDecimal score2;
    private String addr;
    private Integer seat;
    private Boolean passed;
    private String comments;
    private Boolean passed2;
    private Date birthday;


}


