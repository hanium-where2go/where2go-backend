package hanium.where2go.domain.restaurant.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestaurantMenuUpdateRequestDto {

    private String name;
    private Integer price;
    private String content;
    private String img_url;
}
