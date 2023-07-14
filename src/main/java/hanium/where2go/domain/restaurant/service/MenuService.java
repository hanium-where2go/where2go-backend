package hanium.where2go.domain.restaurant.service;

import hanium.where2go.domain.restaurant.dto.MenuResponseDto;
import hanium.where2go.domain.restaurant.entity.Restaurant;
import hanium.where2go.domain.restaurant.repository.RestaurantRepository;
import hanium.where2go.global.response.BaseException;
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

    public List<MenuResponseDto> getMenus(Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new BaseException(404, "Cannot find restaurant id"));

        List<MenuResponseDto> result = restaurant.getMenuList().stream()
                .map(menu -> new MenuResponseDto(menu))
                .collect(Collectors.toList());
        return result;
    }

}
