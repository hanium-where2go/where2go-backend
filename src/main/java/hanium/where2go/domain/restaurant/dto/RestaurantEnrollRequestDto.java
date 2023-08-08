package hanium.where2go.domain.restaurant.dto;


import hanium.where2go.domain.category.dto.CategoryDto;
import hanium.where2go.domain.liquor.dto.LiquorDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestaurantEnrollRequestDto {

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
