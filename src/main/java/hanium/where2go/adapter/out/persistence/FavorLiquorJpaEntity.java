package hanium.where2go.adapter.out.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@Table(name = "favor_liquor")
@NoArgsConstructor
@AllArgsConstructor
public class FavorLiquorJpaEntity extends BaseJpaEntity{

    @Id
    @GeneratedValue
    @Column(name = "favor_liquor_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private CustomerJpaEntity customer;

    @ManyToOne
    @JoinColumn(name = "liquor_id")
    private LiquorJpaEntity liquor;

}
