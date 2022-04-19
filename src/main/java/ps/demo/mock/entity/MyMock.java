

package ps.demo.mock.entity;

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
@Table(name = "my_mock")
public class MyMock extends MyBaseEntity {

    private String uri;
    private Boolean regexMatch;
    private String method;
    private Integer status;
    private String headers;
    private String body;


}


