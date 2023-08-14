package hanium.where2go.domain.restaurant.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestaurantMenuEnrollResponseDto {

      List<Long> menu_Ids;
      List<Long> menu_board_Ids;
}
