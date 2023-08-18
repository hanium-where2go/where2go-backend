package hanium.where2go.domain.restaurant.service;

import hanium.where2go.domain.restaurant.dto.RestaurantDto;
import hanium.where2go.domain.restaurant.dto.RestaurantMenuDto;
import hanium.where2go.domain.restaurant.entity.Menu;
import hanium.where2go.domain.restaurant.entity.MenuBoard;
import hanium.where2go.domain.restaurant.entity.Restaurant;
import hanium.where2go.domain.restaurant.repository.MenuBoardRepository;
import hanium.where2go.domain.restaurant.repository.MenuRepository;
import hanium.where2go.domain.restaurant.repository.RestaurantRepository;
import hanium.where2go.global.response.BaseException;
import hanium.where2go.global.response.ExceptionCode;
import jakarta.transaction.Transactional;
import lombok.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class MenuService {

   private final RestaurantRepository restaurantRepository;
   private final MenuRepository menuRepository;
   private final MenuBoardRepository menuBoardRepository;

    public List<RestaurantMenuDto.MenuResponseDto> getMenus(Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new BaseException(ExceptionCode.RESTAURANT_NOT_FOUND));

       List<RestaurantMenuDto.MenuResponseDto> result = restaurant.getMenuList().stream()
               .map(menu -> new RestaurantMenuDto.MenuResponseDto(menu))
               .collect(Collectors.toList());

       return result;
   }

    // 레스토랑 메뉴 등록
    public RestaurantMenuDto.RestaurantMenuEnrollResponseDto enrollMenus(Long restaurantId, RestaurantMenuDto.RestaurantMenuEnrollRequestDto restaurantMenuEnrollRequestDto){

        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow( () -> new BaseException(ExceptionCode.RESTAURANT_NOT_FOUND));


        List<RestaurantMenuDto.MenuDetailRequestDto> menuDetailList = restaurantMenuEnrollRequestDto.getMenus();
        List<String> imageUrls = restaurantMenuEnrollRequestDto.getMenu_boards();
        List<Menu> menus = new ArrayList<>();
        List<MenuBoard> menuBoards = new ArrayList<>();

        for(RestaurantMenuDto.MenuDetailRequestDto menuDetail : menuDetailList){
            Menu menu = Menu.builder()
                    .restaurant(restaurant)
                    .name(menuDetail.getName())
                    .price(menuDetail.getPrice())
                    .content(menuDetail.getContent())
                    .imgUrl(menuDetail.getImg_url())
                    .build();

            menus.add(menu);
        }
        List<Menu> savedMenus =  menuRepository.saveAll(menus);


        for(String imgUrl : imageUrls)
        {
            MenuBoard menuBoard = new MenuBoard();
            menuBoard.setImageUrl(imgUrl);
            menuBoard.setRestaurant(restaurant);
            menuBoards.add(menuBoard);
        }

        List<MenuBoard> savedMenuBoards = menuBoardRepository.saveAll(menuBoards);


        List<Long> menuIds = savedMenus.stream()
                .map(Menu::getId)
                .collect(Collectors.toList());

        List<Long> menuBoardIds = savedMenuBoards.stream()
                .map(MenuBoard::getId)
                .collect(Collectors.toList());

        return  RestaurantMenuDto.RestaurantMenuEnrollResponseDto.builder()
                .menu_Ids(menuIds)
                .menu_board_Ids(menuBoardIds)
                .build();

    }


    public RestaurantMenuDto.RestaurantMenuUpdateResponseDto updateMenus(Long restaurantId, Long menuId, RestaurantMenuDto.RestaurantMenuUpdateRequestDto restaurantMenuUpdateRequestDto){

        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new BaseException(ExceptionCode.RESTAURANT_NOT_FOUND));

        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new BaseException(ExceptionCode.MENU_NOT_FOUND));

        menu.update(restaurantMenuUpdateRequestDto);
        menuRepository.save(menu);


        return RestaurantMenuDto.RestaurantMenuUpdateResponseDto.builder()
                .menu_id(menu.getId())
                .name(menu.getName())
                .price(menu.getPrice())
                .content(menu.getContent())
                .img_url(menu.getImgUrl())
                .build();
    }


    public RestaurantMenuDto.RestaurantMenuBoardUpdateResponseDto updateMenuBoards(Long restaurantId, Long menuBoardId, RestaurantMenuDto.RestaurantMenuBoardUpdateRequestDto restaurantMenuBoardUpdateRequestDto) {

        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new BaseException(ExceptionCode.RESTAURANT_NOT_FOUND));

        MenuBoard menuBoard = menuBoardRepository.findById(menuBoardId)
                .orElseThrow(() -> new BaseException(ExceptionCode.MENU_BOARD_NOT_FOUND));

        menuBoard.update(restaurantMenuBoardUpdateRequestDto);
        menuBoardRepository.save(menuBoard);

        return RestaurantMenuDto.RestaurantMenuBoardUpdateResponseDto.builder()
                .menu_board_id(menuBoard.getId())
                .build();

    }

    // 하나의 메뉴 상세 조회

    public RestaurantMenuDto.MenuDetailResponseDto getMenuDetail(Long restaurantId, Long menuId){
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new BaseException(ExceptionCode.RESTAURANT_NOT_FOUND));

        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new BaseException(ExceptionCode.MENU_NOT_FOUND));

        RestaurantMenuDto.MenuDetailResponseDto menuDetailResponseDto = RestaurantMenuDto.MenuDetailResponseDto.builder()
                .menu_id(menu.getId())
                .name(menu.getName())
                .price(menu.getPrice())
                .content(menu.getContent())
                .imgUrl(menu.getImgUrl())
                .build();

        return menuDetailResponseDto;
    }

    // 레스토랑 메뉴 삭제

    public void deleteMenu(Long restaurantId, Long menuId){
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new BaseException(ExceptionCode.RESTAURANT_NOT_FOUND));

        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new BaseException(ExceptionCode.MENU_NOT_FOUND));

        menuRepository.delete(menu);
    }

    // 레스토랑 메뉴판 조회

    public RestaurantMenuDto.MenuBoardResponseDto searchMenuBoards(Long restaurantId){
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new BaseException(ExceptionCode.RESTAURANT_NOT_FOUND));

         List<String> imgUrls = restaurant.getMenuBoards().stream()
                 .map(MenuBoard::getImage_url)
                 .collect(Collectors.toList());

         return RestaurantMenuDto.MenuBoardResponseDto.builder()
                 .menuBoards(imgUrls)
                 .build();
    }

}
