package hanium.where2go.domain.restaurant.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuResponseDto {
    private Long menu_id; // 수정된 부분
    private String name;
    private String content;
    private String imgUrl;

    public MenuResponseDto(Long menu_id, String name, String content, String imgUrl) {
        this.menu_id = menu_id;
        this.name = name;
        this.content = content;
        this.imgUrl = imgUrl;
    }
}