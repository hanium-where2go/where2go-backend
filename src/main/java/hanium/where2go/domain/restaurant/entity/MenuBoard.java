package hanium.where2go.domain.restaurant.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@Table(name = "menu_board")
@NoArgsConstructor
@AllArgsConstructor
public class MenuBoard {


    @Id
    @GeneratedValue
    @Column(name = "menu_board_id")
    private Long id;

    private String image_url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    public void setImageUrl(String imageUrl) {
        this.image_url = imageUrl;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}
