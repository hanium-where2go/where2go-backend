package hanium.where2go.domain.restaurant.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MenuDetailRequestDto {

    private String name;
    private Integer price;
    private String content;
    private String img_url;
}
