package hanium.where2go.domain.restaurant.entity;

import hanium.where2go.domain.BaseEntity;
import hanium.where2go.domain.reservation.entity.Review;
import hanium.where2go.domain.restaurant.dto.RestaurantDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<MenuBoard> menuBoards = new ArrayList<>();

    @OneToOne(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private Address address;

    public String restaurantName;
    public String description;
    public String tel;
    public String businessRegistration;
    public int seat; // 남은 자리수

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

    public void update(RestaurantDto.RestaurantUpdateRequestDto restaurantUpdateRequestDto) {
        this.restaurantName = updateField(this.restaurantName, restaurantUpdateRequestDto.getRestaurantName());
        this.location = updateField(this.location, restaurantUpdateRequestDto.getLocation());
        this.start_time = updateField(this.start_time, restaurantUpdateRequestDto.getStartTime());
        this.end_time = updateField(this.end_time, restaurantUpdateRequestDto.getEndTime());
        this.closed_day = updateField(this.closed_day, restaurantUpdateRequestDto.getClosedDay());
        this.tel = updateField(this.tel, restaurantUpdateRequestDto.getTel());
        this.total_seat = updateField(this.total_seat, restaurantUpdateRequestDto.getTotalSeat());
        this.onetime_Seat = updateField(this.onetime_Seat, restaurantUpdateRequestDto.getOnetimeSeat());
        this.parkingLot = updateField(this.parkingLot, restaurantUpdateRequestDto.getParkingLot());
    }

    private <T> T updateField(T currentValue, T newValue) {
        return newValue != null ? newValue : currentValue;
    }


}