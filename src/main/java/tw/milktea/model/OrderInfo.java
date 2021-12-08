package tw.milktea.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Table(name = "order_info")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class OrderInfo implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "id", columnDefinition = "INT(12)")
    private Long id;

    @Column(name = "order_id")
    private String orderId;

    @Column(name = "status_id")
    private String status;

    @OneToMany(targetEntity = Products.class, cascade = CascadeType.ALL)
    List<Products> products;

    private BigDecimal totalPrice;

    @Column(name = "deleted")
    private boolean deleted;
}
