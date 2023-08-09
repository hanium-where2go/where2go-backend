package hanium.where2go.domain.restaurant.entity;

import hanium.where2go.domain.BaseEntity;
import hanium.where2go.domain.liquor.entity.Liquor;
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
public class RestaurantLiquor extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "restaurant_liquor_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "liquor_id")
    private Liquor liquor;

    public RestaurantLiquor(Restaurant restaurant, Liquor liquor) {
        this.restaurant = restaurant;
        this.liquor = liquor;
    }

    public void setRestaurant(Restaurant savedRestaurant) {
        this.restaurant = savedRestaurant;
    }

    public void setLiquor(Liquor liquor) {
        this.liquor = liquor;
    }
}
