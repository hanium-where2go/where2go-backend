package hanium.where2go.domain.restaurant.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommonInformationResponseDto {

    private String restaurantName;
    private String restaurantImage;
    private String title; // 이벤트의 제목이다
    private String description;
    private double responseAvg;
    private double rateAvg;
    private int seat;
}
