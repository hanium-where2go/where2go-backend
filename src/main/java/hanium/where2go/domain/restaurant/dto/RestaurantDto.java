package hanium.where2go.domain.restaurant.dto;

import lombok.*;

import java.time.LocalTime;
import java.util.List;

public class RestaurantDto {
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CommonInformationResponseDto {
        private String restaurantName;
        private String restaurantImage;
        private String title; // 이벤트의 제목이다
        private String description;
        private double responseAvg;
        private double rateAvg;
        private int seat;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class InformationResponseDto {
        private String location;
        private String description;
        private String tel;
        private Boolean parkingLot;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class RestaurantEnrollRequestDto {
        private String restaurantName;
        private MapDto.Address address;
        private List<String> categoryNames;
        private List<String> liquorNames;
        private LocalTime startTime;
        private LocalTime endTime;
        private String closedDay;
        private String tel;
        private Integer totalSeat;
        private Integer onetimeSeat;
        private Boolean parkingLot;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RestaurantEnrollResponseDto {
        private Long restaurantId;
        private String name;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RestaurantUpdateRequestDto {
        private String restaurantName;
        private String location; // todo MapDto.Address로 변경
        private List<String> categoryNames;
        private List<String> liquorNames;
        private LocalTime startTime;
        private LocalTime endTime;
        private String closedDay;
        private String tel;
        private Integer totalSeat;
        private Integer onetimeSeat;
        private Boolean parkingLot;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class RestaurantUpdateResponseDto {
        private Long restaurantId;
        private String name;
    }
}
