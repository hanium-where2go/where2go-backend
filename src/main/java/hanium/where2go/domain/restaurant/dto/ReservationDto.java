package hanium.where2go.domain.restaurant.dto;

import hanium.where2go.domain.reservation.entity.Rejection;
import hanium.where2go.domain.reservation.entity.Reservation;
import hanium.where2go.domain.reservation.entity.ReservationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class ReservationDto {
    // ReservationRequestDto 정의

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ReservationRequestDto {
        private Integer numberOfPeople;
        private Integer reservation_time;
        private String content;
    }

    // ReservationResponseDto 정의
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ReservationResponseDto {
       private Long reservationId;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class updateReservationStatus{
        private ReservationStatus reservationStatus;
        private Rejection rejection;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ReservationInformationResponseDto{
        private Long restaurantId;
        private String restaurantName;
        private LocalDate reservationDate;
        private Integer numberOfPeople;
        private LocalTime reservationTime;
        private LocalTime arrivingTime;
        private String content;
        private String phoneNumber;
        private String username;
        private ReservationStatus reservationStatus;
    }

}
