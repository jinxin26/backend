package tw.milktea.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "products")
public class Products {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "sugar")
    private String sugar;

    @Column(name = "heat")
    private String heat;
}
