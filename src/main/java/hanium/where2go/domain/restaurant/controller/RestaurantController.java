package hanium.where2go.domain.restaurant.controller;

import hanium.where2go.domain.restaurant.dto.InformationResponseDto;
import hanium.where2go.domain.restaurant.dto.MenuResponseDto;
import hanium.where2go.domain.restaurant.service.InformationService;
import hanium.where2go.domain.restaurant.service.MenuService;
import hanium.where2go.global.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/restaurants")
public class RestaurantController {

   private final MenuService menuService;
   private final InformationService informationService;


   @GetMapping("/{restaurantId}/menu")
    public BaseResponse<List<MenuResponseDto>> menu (@PathVariable("restaurantId") Long restaurantId){
       List<MenuResponseDto> list = menuService.getMenus(restaurantId);
       return new BaseResponse<>(200,"메뉴를 불러왔습니다", list);
   }

   @GetMapping("/{restaurantId}/information")
   public BaseResponse<InformationResponseDto> information(@PathVariable("restaurantId") Long restaurantId){
      InformationResponseDto information = informationService.getInformation(restaurantId);

      return new BaseResponse<>(200,"가게 정보를 불러왔습니다",information);
   }
}
