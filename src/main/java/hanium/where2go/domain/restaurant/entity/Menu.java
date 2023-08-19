package hanium.where2go.domain.restaurant.entity;

import hanium.where2go.domain.BaseEntity;
import hanium.where2go.domain.restaurant.dto.RestaurantMenuDto;
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
        restaurant.getMenuList().add(this);
    }

    public void update(RestaurantMenuDto.RestaurantMenuUpdateRequestDto restaurantMenuUpdateRequestDto){
        this.name = updateField(this.name, restaurantMenuUpdateRequestDto.getName());
        this.price = updateField(this.price, restaurantMenuUpdateRequestDto.getPrice());
        this.content = updateField(this.content, restaurantMenuUpdateRequestDto.getContent());
        this.imgUrl = updateField(this.imgUrl, restaurantMenuUpdateRequestDto.getImg_url());
    }

    private <T> T updateField(T currentValue, T newValue){
        return newValue !=null ? newValue : currentValue;
    }
}
