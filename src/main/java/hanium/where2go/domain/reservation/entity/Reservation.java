package hanium.where2go.domain.reservation.entity;

import hanium.where2go.domain.BaseEntity;
import hanium.where2go.domain.customer.entity.Customer;
import hanium.where2go.domain.restaurant.entity.Restaurant;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToOne(mappedBy = "reservation", fetch = FetchType.LAZY)
    private Review review;

    private Integer numberOfPeople;
    private Integer reservationTime;
    private String content;

    // 예약 상태를 Enum으로 지정
    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    @Enumerated(EnumType.STRING)
    private Rejection rejection;

    private String reservationNumber;


    public void setStatus(ReservationStatus status) {
        this.status = status;
    }

    // setRejection 메서드 추가
    public void setRejection(Rejection rejection) {
        this.rejection = rejection;
    }

    public void setReservationNumber(String reservationNumber){
        this.reservationNumber = reservationNumber;
    }
}
