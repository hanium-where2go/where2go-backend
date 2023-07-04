package hanium.where2go.adapter.out.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Builder
@Table(name = "reservation")
@NoArgsConstructor
@AllArgsConstructor
public class ReservationJpaEntity extends BaseJpaEntity{

    @Id
    @GeneratedValue
    @Column(name = "reservation_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private RestaurantJpaEntity restaurant;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private CustomerJpaEntity customer;

    private Integer numberOfPeople;
    private LocalDateTime reservationTime;
    private String content;
    //예약 상태 -> Enum
    private String status;
    private String rejection;
}
