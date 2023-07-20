package hanium.where2go.domain.restaurant.dto;

import hanium.where2go.domain.restaurant.entity.Restaurant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InformationResponseDto {

    private String location;
    private String description;
    private String tel;
    private Boolean parkingLot;

}
