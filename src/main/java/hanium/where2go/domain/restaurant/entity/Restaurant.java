package hanium.where2go.domain.restaurant.entity;

import hanium.where2go.domain.BaseEntity;
import hanium.where2go.domain.reservation.entity.Review;
import hanium.where2go.domain.restaurant.dto.InformationResponseDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalTime;
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

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<Review> review = new ArrayList<>();

    public String restaurantName;
    public String address;
    public String description;
    public String tel;
    public String businessRegistration;
    public int seat; // 남은 자리수
    public BigDecimal longitude;
    public BigDecimal latitude;
    public String location;
    public Boolean parkingLot;
    public String restaurantImage;
    private double responseAvg;
    private double rateAvg;
    public LocalTime start_time;
    public LocalTime end_time;
    public String closed_day;
    public Integer total_seat; // 가게의 총 좌석수
    public Integer onetime_Seat; // 단일로 예약 가능한 최대 좌석수

    public void setRestaurantCategories(List<RestaurantCategory> restaurantCategories) {
        this.restaurantCategories = restaurantCategories;
    }

    public void setRestaurantLiquors(List<RestaurantLiquor> restaurantLiquors) {
        this.restaurantLiquors = restaurantLiquors;
    }
}