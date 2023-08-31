package hanium.where2go.domain.restaurant.dto;

import hanium.where2go.domain.reservation.entity.ReservationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class ReservationDto {


    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ReservationRequestDto {
        private Integer numberOfPeople;
        private Integer reservation_time;
        private String content;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ReservationResponseDto {

        private ReservationStatus status;
        private boolean confirm_num;
        private String rejection;
        private Integer reservation_id;

    }

    public static class ReservationDetailResponseDto{
         private Long restaurant_id;
         private String restaurant_name;
         private LocalDate reservation_date;
         private Integer numberOfPeople;
         private LocalTime reservation_time;
         private LocalTime arriving_time;
         private String content;
         private String phoneNumber;
         private String username;
         private ReservationStatus status;

    }


}
