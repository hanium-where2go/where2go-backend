package hanium.where2go.domain.restaurant.dto;

import hanium.where2go.domain.restaurant.entity.Menu;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MenuResponseDto {

    private String name;
    private int price;
    private String content;
    private String imgUrl;

    public MenuResponseDto(Menu menu) {
        this.name = menu.getName();
        this.price = menu.getPrice();
        this.content = menu.getContent();
        this.imgUrl = menu.getImgUrl();
    }

}
