package hanium.where2go.domain.restaurant.entity;

import hanium.where2go.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@Table(name = "menu")
@NoArgsConstructor
@AllArgsConstructor
public class Menu extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "menu_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    private String name;
    private int price;
    private String content;
    private String imgUrl;

    // 내가 추가한거
    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}
