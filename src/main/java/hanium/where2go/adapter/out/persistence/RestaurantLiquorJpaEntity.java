package hanium.where2go.adapter.out.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@Table(name = "restaurant_liquor")
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantLiquorJpaEntity extends BaseJpaEntity{

    @Id
    @GeneratedValue
    @Column(name = "restaurant_liquor_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private RestaurantJpaEntity restaurant;

    @ManyToOne
    @JoinColumn(name = "liquor_id")
    private LiquorJpaEntity liquor;
}
