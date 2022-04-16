package ps.demo.order.entity;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ps.demo.common.MyBaseEntity;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
@Table(name = "cart_line")
public class CartLine extends MyBaseEntity {

//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Long id;

    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private NewCart newCart;

}
