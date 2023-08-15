package hanium.where2go.domain.restaurant.service;

import hanium.where2go.domain.restaurant.dto.RestaurantMenuDto;
import hanium.where2go.domain.restaurant.entity.Restaurant;
import hanium.where2go.domain.restaurant.repository.RestaurantRepository;
import hanium.where2go.global.response.BaseException;
import hanium.where2go.global.response.ExceptionCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class MenuService {

   private final RestaurantRepository restaurantRepository;

    public List<RestaurantMenuDto.MenuResponseDto> getMenus(Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new BaseException(ExceptionCode.RESTAURANT_NOT_FOUND));

       List<RestaurantMenuDto.MenuResponseDto> result = restaurant.getMenuList().stream()
               .map(menu -> new RestaurantMenuDto.MenuResponseDto(menu))
               .collect(Collectors.toList());

       return result;
   }
}
