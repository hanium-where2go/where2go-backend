package hanium.where2go.domain.restaurant.dto;


import hanium.where2go.domain.restaurant.entity.Restaurant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantUpdateRequestDto {
    private String restaurantName;
    private String location;
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
