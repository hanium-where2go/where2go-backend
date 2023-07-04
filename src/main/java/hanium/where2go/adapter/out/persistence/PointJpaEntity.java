package hanium.where2go.adapter.out.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@Table(name = "point")
@NoArgsConstructor
@AllArgsConstructor
public class PointJpaEntity extends BaseJpaEntity{

    @Id
    @GeneratedValue
    @Column(name = "point_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private CustomerJpaEntity customer;

    private int amount;
}
