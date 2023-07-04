package hanium.where2go.domain;

import java.time.LocalDateTime;
import java.util.Date;

public class Reservation {

    private Long id;
    private Restaurant restaurant;
    private Customer customer;
    private Integer numberOfPeople;
    private LocalDateTime reservationTime;
    private String content;
    //예약 상태 -> Enum
    private String status;
    private String rejection;
}
