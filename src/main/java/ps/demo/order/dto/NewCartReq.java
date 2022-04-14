package ps.demo.order.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@ToString
public class NewCartReq implements Serializable {

    private Long id;

    @NotNull
    private Long accountId;

    private Long supplyCenterId;

    @Valid
    @NotEmpty
    private List<CartLineDto> cartLineList;

}
