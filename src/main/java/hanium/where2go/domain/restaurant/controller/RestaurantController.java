package hanium.where2go.domain.restaurant.controller;

import hanium.where2go.domain.restaurant.dto.*;
import hanium.where2go.domain.restaurant.service.MenuService;
import hanium.where2go.domain.restaurant.service.RestaurantService;
import hanium.where2go.global.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/restaurants")
public class RestaurantController {

   private final MenuService menuService;
   private final RestaurantService restaurantService;

    // 레스토랑 메뉴 조회
   @GetMapping("/{restaurantId}/menu")
    public ResponseEntity<BaseResponse<List<RestaurantMenuDto.MenuResponseDto>>> menu (@PathVariable("restaurantId") Long restaurantId){
       List<RestaurantMenuDto.MenuResponseDto> list = menuService.getMenus(restaurantId);

      return ResponseEntity
              .status(HttpStatus.OK)
              .body(new BaseResponse<>(HttpStatus.OK.value(),"가게 메뉴를 불러왔습니다",list));
   }

    // 레스토랑 정보 조회
   @GetMapping("/{restaurantId}/information")
   public ResponseEntity<BaseResponse<RestaurantDto.InformationResponseDto>> information(@PathVariable("restaurantId") Long restaurantId){
       RestaurantDto.InformationResponseDto information = restaurantService.getInformation(restaurantId);

      return ResponseEntity
              .status(HttpStatus.OK)
              .body(new BaseResponse<>(HttpStatus.OK.value(),"가게 정보를 불러왔습니다",information));
   }

    // 레스토랑 공통 정보 조회
   @GetMapping("/{restaurantId}")
   public ResponseEntity<BaseResponse<RestaurantDto.CommonInformationResponseDto>> commonInformation(@PathVariable("restaurantId") Long restaurantId){
      RestaurantDto.CommonInformationResponseDto commonInformation = restaurantService.getCommonInformation(restaurantId);

      return ResponseEntity
              .status(HttpStatus.OK)
              .body(new BaseResponse<>(HttpStatus.OK.value(),"가게 공통 정보를 불러왔습니다",commonInformation));

   }

   // 레스토랑 정보 등록
   @PostMapping
   public ResponseEntity<BaseResponse<RestaurantDto.RestaurantEnrollResponseDto>> restaurantEnroll(@RequestBody RestaurantDto.RestaurantEnrollRequestDto restaurantEnrollDto){
          RestaurantDto.RestaurantEnrollResponseDto restaurantEnrollResponseDto = restaurantService.enrollRestaurant(restaurantEnrollDto);

          return ResponseEntity
                  .status(HttpStatus.OK)
                  .body(new BaseResponse<>(HttpStatus.OK.value(), "가게 공통 정보를 불러왔습니다", restaurantEnrollResponseDto ));
   }

   // 레스토랑 정보 수정
    @PatchMapping("/{restaurantId}")
    public ResponseEntity<BaseResponse<RestaurantDto.RestaurantUpdateResponseDto>> updateRestaurantInfo(@PathVariable("restaurantId") Long restaurantId, @RequestBody RestaurantDto.RestaurantUpdateRequestDto restaurantUpdateRequestDto){

       RestaurantDto.RestaurantUpdateResponseDto restaurantUpdateResponseDto = restaurantService.updateRestaurantInfo(restaurantId,restaurantUpdateRequestDto);

       return ResponseEntity
               .status(HttpStatus.OK)
               .body(new BaseResponse<>(HttpStatus.OK.value(),"가게 정보를 수정하였습니다", restaurantUpdateResponseDto));
    }

    // 레스토랑 메뉴 등록

    @PostMapping("/{restaurantId}/menu")
    public ResponseEntity<BaseResponse<RestaurantMenuDto.RestaurantMenuEnrollResponseDto>> enrollMenus(@PathVariable("restaurantId") Long restaurantId, @RequestBody RestaurantMenuDto.RestaurantMenuEnrollRequestDto restaurantMenuEnrollRequestDto){

    RestaurantMenuDto.RestaurantMenuEnrollResponseDto restaurantMenuEnrollResponseDto =  restaurantService.enrollMenus(restaurantId, restaurantMenuEnrollRequestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new BaseResponse<>(HttpStatus.OK.value(),"메뉴 정보를 등록하였습니다", restaurantMenuEnrollResponseDto));
    }

    //레스토랑 메뉴 수정
    @PatchMapping("{restaurantId}/menu/{menuId}")
    public ResponseEntity<BaseResponse<RestaurantMenuDto.RestaurantMenuUpdateResponseDto>> updateMenus(@PathVariable("restaurantId") Long restaurantId, @PathVariable("menuId") Long menuId, @RequestBody RestaurantMenuDto.RestaurantMenuUpdateRequestDto restaurantMenuUpdateRequestDto){

       RestaurantMenuDto.RestaurantMenuUpdateResponseDto restaurantMenuUpdateResponseDto = restaurantService.updateMenus(restaurantId,menuId, restaurantMenuUpdateRequestDto);

       return ResponseEntity
               .status(HttpStatus.OK)
               .body(new BaseResponse<>(HttpStatus.OK.value(), "메뉴 정보를 수정하였습니다", restaurantMenuUpdateResponseDto));
    }

    //레스토랑 메뉴판 수정
    @PatchMapping("{restaurantId}/menuBoard/{menuBoardId}")
    public ResponseEntity<BaseResponse<RestaurantMenuDto.RestaurantMenuBoardUpdateResponseDto>> updateMenuBoards(@PathVariable("restaurantId") Long restaurantId, @PathVariable("menuBoardId") Long menuBoardId, @RequestBody RestaurantMenuDto.RestaurantMenuBoardUpdateRequestDto restaurantMenuBoardUpdateRequestDto){

       RestaurantMenuDto.RestaurantMenuBoardUpdateResponseDto restaurantMenuBoardUpdateResponseDto = restaurantService.updateMenuBoards(restaurantId,menuBoardId,restaurantMenuBoardUpdateRequestDto);

       return ResponseEntity
               .status(HttpStatus.OK)
               .body(new BaseResponse<>(HttpStatus.OK.value(), "메뉴판 정보를 수정하였습니다", restaurantMenuBoardUpdateResponseDto));

    }
}
