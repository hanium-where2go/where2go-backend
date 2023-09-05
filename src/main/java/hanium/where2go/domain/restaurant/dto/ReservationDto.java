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
        private int status;
        private String message;
        private Data data;

        // 게터 및 세터 메서드

        @Getter
        @NoArgsConstructor
        @AllArgsConstructor
        @Builder
        public static class Data {
            private String status;
            private String rejection;
            private int confirm_num;
            private int reservation_id;

            // 게터 및 세터 메서드
        }
    }
}
