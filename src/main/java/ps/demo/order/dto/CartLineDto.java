package ps.demo.order.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@ToString
public class CartLineDto implements Serializable {

    private Long id;

    private Long cartId;

    @NotNull
    private Long productId;

    @NotNull
    private Integer quantity;

}
