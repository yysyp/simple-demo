

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
    private String rawPeriod;
    private String rawKemu;
    private String kemu;
    private String rawKemuValue;
    private BigDecimal kemuValue;
    private BigDecimal yoy;
    private BigDecimal percentOnXx;
    private Integer flag;
    private String fileName;
    private String comments;


}





