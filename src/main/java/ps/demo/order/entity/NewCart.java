package ps.demo.order.entity;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ps.demo.common.MyBaseEntity;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@ToString
@Entity
@Table(name = "new_cart")
public class NewCart extends MyBaseEntity {

//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Long id;

    @Column(name = "account_id", nullable = false)
    private Long accountId;

    @Column(name = "supply_center_id", nullable = false)
    private Long supplyCenterId;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "newCart")
    private List<CartLine> cartLineList;


}
