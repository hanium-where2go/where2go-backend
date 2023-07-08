package hanium.where2go.domain.restaurant.entity;

import hanium.where2go.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@Table(name = "restaurant")
@NoArgsConstructor
@AllArgsConstructor
public class Restaurant extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "restaurant_id")
    public Long restaurantId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    public Owner owner;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<Menu> menuList = new ArrayList<>();

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<RestaurantLiquor> restaurantLiquors = new ArrayList<>();

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<RestaurantCategory> restaurantCategories = new ArrayList<>();

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<Event> events = new ArrayList<>();

    public String restaurantName;
    public String address;
    public String description;
    public String tel;
    public String businessRegistration;
    public int seat;
    public BigDecimal longitude;
    public BigDecimal latitude;
}