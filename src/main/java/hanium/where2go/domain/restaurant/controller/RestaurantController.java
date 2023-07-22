package hanium.where2go.domain.restaurant.controller;

import hanium.where2go.domain.restaurant.dto.CommonInformationResponseDto;
import hanium.where2go.domain.restaurant.dto.InformationResponseDto;
import hanium.where2go.domain.restaurant.dto.MenuResponseDto;
import hanium.where2go.domain.restaurant.service.MenuService;
import hanium.where2go.domain.restaurant.service.RestaurantService;
import hanium.where2go.global.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
   private final RestaurantService restaurantService;


   @GetMapping("/{restaurantId}/menu")
    public ResponseEntity<BaseResponse<List<MenuResponseDto>>> menu (@PathVariable("restaurantId") Long restaurantId){
       List<MenuResponseDto> list = menuService.getMenus(restaurantId);

      return ResponseEntity
              .status(HttpStatus.OK)
              .body(new BaseResponse<>(HttpStatus.OK.value(),"가게 메뉴를 불러왔습니다",list));
   }

   @GetMapping("/{restaurantId}/information")
   public ResponseEntity<BaseResponse<InformationResponseDto>> information(@PathVariable("restaurantId") Long restaurantId){
      InformationResponseDto information = restaurantService.getInformation(restaurantId);

      return ResponseEntity
              .status(HttpStatus.OK)
              .body(new BaseResponse<>(HttpStatus.OK.value(),"가게 정보를 불러왔습니다",information));
   }

   @GetMapping("/{restaurantId}")
   public ResponseEntity<BaseResponse<CommonInformationResponseDto>> commonInformation(@PathVariable("restaurantId") Long restaurantId){
      CommonInformationResponseDto commonInformation = restaurantService.getCommonInformation(restaurantId);

      return ResponseEntity
              .status(HttpStatus.OK)
              .body(new BaseResponse<>(HttpStatus.OK.value(),"가게 공통 정보를 불러왔습니다",commonInformation));

   }

}
