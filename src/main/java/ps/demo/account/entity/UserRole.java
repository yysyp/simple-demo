

package ps.demo.account.entity;

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
@Table(name = "user_role")
public class UserRole extends MyBaseEntity {

    private Long userId;
    private Long roleId;
    private String roleName;


}


