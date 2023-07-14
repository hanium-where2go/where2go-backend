package hanium.where2go.domain.restaurant.controller;

import hanium.where2go.domain.restaurant.dto.MenuResponseDto;
import hanium.where2go.domain.restaurant.service.MenuService;
import hanium.where2go.global.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/restaurants")
public class RestaurantController {

    private final MenuService menuService;


    @GetMapping("/{restaurantId}/menu")
    public BaseResponse<List<MenuResponseDto>> menu(@PathVariable("restaurantId") Long restaurantId){

        List<MenuResponseDto> list = menuService.getMenus(restaurantId);

        return new BaseResponse<>(200,"메뉴를 불러왔습니다",list);

    }


}
