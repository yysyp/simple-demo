package ps.demo.order.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

@Getter
@Setter
@ToString
public class NewCartRes implements Serializable {

    private Long id;

    private Long accountId;

    private Long supplyCenterId;

    private List<CartLineDto> cartLineList;

}
