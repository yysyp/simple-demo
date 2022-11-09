

package ps.demo.newstock.dto;

import lombok.*;
import java.util.*;
import java.math.*;

import ps.demo.common.MyPageReq;


@ToString
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class NewStockDataReq extends MyPageReq {
    private String key;

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





