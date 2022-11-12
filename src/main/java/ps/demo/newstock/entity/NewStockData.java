

package ps.demo.newstock.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

import ps.demo.common.MyBaseEntity;

import javax.persistence.Column;
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

    @Column(name = "kemu_value", precision = 19, scale = 4)
    private BigDecimal kemuValue;
    @Column(name = "yoy", precision = 19, scale = 4)
    private BigDecimal yoy;
    @Column(name = "pct_in_asset_or_revenue", precision = 19, scale = 4)
    private BigDecimal pctInAssetOrRevenue;
    @Column(name = "core_profit_on_asset_effect", precision = 19, scale = 4)
    private BigDecimal coreProfitOnAssetEffect;
    private Integer flag;
    private String fileName;
    private String comments;


}


