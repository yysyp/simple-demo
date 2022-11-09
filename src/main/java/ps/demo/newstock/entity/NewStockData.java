

package ps.demo.newstock.entity;

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
@Table(name = "new_stock_data")
public class NewStockData extends MyBaseEntity {

    private String companyCode;
    private Integer periodYear;
    private Integer periodMonth;
    private String companyName;
    private String originalPeriod;
    private String originalKemu;
    private String kemu;
    private String originalKemuValue;
    private BigDecimal kemuValue;
    private BigDecimal yoy;
    private String fileName;
    private String comments;


}


