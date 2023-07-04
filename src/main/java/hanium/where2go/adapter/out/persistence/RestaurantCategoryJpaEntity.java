package hanium.where2go.adapter.out.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@Table(name = "reservation_category")
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantCategoryJpaEntity extends BaseJpaEntity{

    @Id
    @GeneratedValue
    @Column(name = "restaurant_category_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private RestaurantJpaEntity restaurant;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryJpaEntity category;
}
