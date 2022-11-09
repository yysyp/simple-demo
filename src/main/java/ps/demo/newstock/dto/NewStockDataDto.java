

package ps.demo.newstock.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.Date;
import java.util.List;
import java.util.*;
import java.math.*;
import ps.demo.common.MyBaseDto;
import lombok.*;
import java.util.*;
import java.math.*;
@Getter
@Setter
@ToString
public class NewStockDataDto extends MyBaseDto {

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


