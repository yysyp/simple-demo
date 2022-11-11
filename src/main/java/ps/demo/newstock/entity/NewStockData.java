

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
    private String rawPeriod;
    private String kemuType;
    private String rawKemu;
    private String kemuEn;
    private String kemu;
    private String rawKemuValue;
    private BigDecimal kemuValue;
    private BigDecimal yoy;
    private BigDecimal pctInAssetOrRevenue;
    private BigDecimal coreProfitOnAssetEffect;
    private Integer flag;
    private String fileName;
    private String comments;


}


