package hanium.where2go.domain.restaurant.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @Column(nullable = false)
    private String roadAddr;

    @Column(nullable = false)
    private String jibunAddr;

    @Column(nullable = false)
    private String longitude;

    @Column(nullable = false)
    private String latitude;

    public void updateRestaurant(Restaurant restaurant){
        this.restaurant = restaurant;
        restaurant.updateAddress(this);
    }
}