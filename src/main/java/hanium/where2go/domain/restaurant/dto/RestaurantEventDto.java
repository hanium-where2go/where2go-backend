package hanium.where2go.domain.restaurant.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class RestaurantEventDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class EventEnrollRequestDto {

        private String title;
        private String content;
        private LocalDate startDate;
        private LocalDate endDate;

    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class EventEnrollResponseDto{
        private Long eventId;
    }


    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EventUpdateRequestDto {

        private String title;
        private String content;
        private LocalDate startDate;
        private LocalDate endDate;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class EventUpdateResponseDto {
        private Long eventId;
        private String restaurantName;
        private String title;
        private String content;
        private LocalDate startDate;
        private LocalDate endDate;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class EventSearchResponseDto{

        private Long eventId;
        private String title;
        private String content;
        private LocalDate startDate;
        private LocalDate endDate;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class SingleEventSearchResponseDto{
        private Long eventId;
        private String restaurantName;
        private String title;
        private String content;
        private LocalDate startDate;
        private LocalDate endDate;
    }
}
