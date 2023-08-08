package hanium.where2go.domain.restaurant.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestaurantUpdateResponseDto {

    private Long restaurantId;
    private String name;
}
