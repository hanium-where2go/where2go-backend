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
public class RestaurantMenuEnrollRequestDto {

    private List<MenuDetailRequestDto> menus; // 대표 메뉴 등록
    private List<String> menu_boards; // 가게의 메뉴판 등록
}
