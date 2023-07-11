package hanium.where2go.domain.reservation.entity;

import hanium.where2go.domain.BaseEntity;
import hanium.where2go.domain.customer.entity.Customer;
import hanium.where2go.domain.restaurant.entity.Owner;
import hanium.where2go.domain.restaurant.entity.Restaurant;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Entity
@Getter
@Builder
@Table(name = "reservation")
@NoArgsConstructor
@AllArgsConstructor
public class Reservation extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "reservation_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

    private Integer numberOfPeople;
    private LocalDateTime reservationTime;
    private String content;
    //예약 상태 -> Enum
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private Owner owner; // Owner와의 관계 매핑

    private Long confirmNum; // 이 부분 추가 했음. 예약 확인 번호임

    @PrePersist
    public void generateConfirmNum() { // 예약 번호 랜덤을 나오게 하기
        Random random = new Random();
        this.confirmNum = Math.abs(random.nextInt(90000000)) + 10000000L;
    }
}
