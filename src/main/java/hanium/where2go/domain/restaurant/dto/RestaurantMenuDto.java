package hanium.where2go.domain.restaurant.dto;

import hanium.where2go.domain.restaurant.entity.Menu;
import hanium.where2go.domain.restaurant.entity.MenuBoard;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class RestaurantMenuDto {
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class RestaurantMenuEnrollRequestDto {

        private List<MenuDetailRequestDto> menus; // 대표 메뉴 등록
        private List<String> menu_boards; // 가게의 메뉴판 등록
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class RestaurantMenuEnrollResponseDto {

        List<Long> menu_Ids;
        List<Long> menu_board_Ids;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class RestaurantMenuUpdateRequestDto {

        private String name;
        private Integer price;
        private String content;
        private String img_url;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class RestaurantMenuUpdateResponseDto {

        private Long menu_id;
        private String name;
        private Integer price;
        private String content;
        private String img_url;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class RestaurantMenuBoardUpdateRequestDto {

        private String image_url;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class RestaurantMenuBoardUpdateResponseDto {

        private Long menu_board_id;
        private String img_url;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MenuDetailRequestDto {

        private String name;
        private Integer price;
        private String content;
        private String img_url;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MenuResponseDto {

        private Long menuId;
        private String name;
        private int price;
        private String content;
        private String imgUrl;
        public MenuResponseDto(Menu menu) {
            this.menuId = menu.getId();
            this.name = menu.getName();
            this.price = menu.getPrice();
            this.content = menu.getContent();
            this.imgUrl = menu.getImgUrl();
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class MenuDetailResponseDto {

        private Long menu_id;
        private String name;
        private Integer price;
        private String content;
        private String imgUrl;

    }

   @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class MenuBoardResponseDto{
        List<String> menuBoards;
   }
}
