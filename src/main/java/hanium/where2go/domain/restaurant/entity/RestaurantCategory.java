package hanium.where2go.domain.restaurant.entity;

import hanium.where2go.domain.BaseEntity;
import hanium.where2go.domain.category.entity.Category;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@Table(name = "restaurant_category")
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantCategory extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "restaurant_category_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;


    public RestaurantCategory(Restaurant restaurant, Category category) {
        this.restaurant = restaurant;
        this.category = category;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
