package hanium.where2go.adapter.out.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@Table(name = "transaction")
@NoArgsConstructor
@AllArgsConstructor
public class TransactionJpaEntity extends BaseJpaEntity{

    @Id
    @GeneratedValue
    @Column(name = "transaction_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer")
    private CustomerJpaEntity customer;

    @ManyToOne
    @JoinColumn(name = "point_id")
    private PointJpaEntity point;
    //거래 타입 -> Enum
    private String type;
    private int amount;
    private String description;
}
