package ps.demo.system.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

import ps.demo.common.MyBaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.*;
import java.math.*;


@Getter
@Setter
@ToString
@Entity
@Table(name = "system_tracking")
public class SystemTracking extends MyBaseEntity {

    private String countSource;

    private String fetchSourceByPage;

    private String insertTarget;

    private String selectTargetMd5List;

    private String deleteTargetByMd5;

}
