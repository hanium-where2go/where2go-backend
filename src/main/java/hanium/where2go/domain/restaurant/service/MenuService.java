package hanium.where2go.domain.restaurant.service;

import hanium.where2go.domain.restaurant.dto.MenuResponseDto;
import hanium.where2go.domain.restaurant.entity.Menu;
import hanium.where2go.domain.restaurant.repository.MenuRepository;
import hanium.where2go.global.response.BaseException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MenuService {

    @Autowired
    private final MenuRepository menuRepository;

    public List<MenuResponseDto> getMenusByRestaurantId(Long restaurantId) {
        List<Menu> menus = menuRepository.findByRestaurantRestaurantId(restaurantId);
        if (menus.isEmpty()) {
            throw new BaseException(404, "메뉴 정보를 찾지 못하였습니다");
        }
        return menus.stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }


    private MenuResponseDto convertToResponseDto(Menu menu) {
        return new MenuResponseDto(
                menu.getId(), // 수정된 부분
                menu.getName(),
                menu.getContent(),
                menu.getImgUrl()
        );
    }
}