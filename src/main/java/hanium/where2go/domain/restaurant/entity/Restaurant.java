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
@NoArgsConstructor
@AllArgsConstructor
public class Restaurant extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long restaurantId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    public Owner owner;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Menu> menuList = new ArrayList<>();

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RestaurantLiquor> restaurantLiquors = new ArrayList<>();

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RestaurantCategory> restaurantCategories = new ArrayList<>();

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Event> events = new ArrayList<>();

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> review = new ArrayList<>();

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MenuBoard> menuBoards = new ArrayList<>();

    @OneToOne(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private Address address;

    @Column
    private String restaurantName;
    @Column
    private String description;
    @Column
    private String tel;
    @Column
    private String businessRegistration;
    @Column
    private int seat; // 남은 자리수
    @Column
    private Boolean parkingLot;
    @Column
    private String restaurantImage;
    @Column
    private double responseAvg;
    @Column
    private double rateAvg;
    @Column
    private LocalTime start_time;
    @Column
    private LocalTime end_time;
    @Column
    private String closed_day;
    @Column
    private Integer total_seat; // 가게의 총 좌석수
    @Column
    private Integer onetime_Seat; // 단일로 예약 가능한 최대 좌석수

    public void setRestaurantCategories(List<RestaurantCategory> restaurantCategories) {
        this.restaurantCategories = restaurantCategories;
    }

    public void setRestaurantLiquors(List<RestaurantLiquor> restaurantLiquors) {
        this.restaurantLiquors = restaurantLiquors;
    }

    public void update(RestaurantDto.RestaurantUpdateRequestDto restaurantUpdateRequestDto) {
        this.restaurantName = updateField(this.restaurantName, restaurantUpdateRequestDto.getRestaurantName());
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

    public void updateAddress(Address address) {
        this.address = address;
    }

    public Long getId() {
        return restaurantId;
    }
}