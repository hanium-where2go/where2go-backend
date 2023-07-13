package hanium.where2go.domain.restaurant.controller;


import hanium.where2go.domain.restaurant.dto.MenuResponseDto;
import hanium.where2go.domain.restaurant.service.MenuService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MenuController {

    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping("/restaurants/{restaurantId}/menu")
    public ResponseEntity<List<MenuResponseDto>> getMenusByRestaurantId(@PathVariable Long restaurantId) {
        List<MenuResponseDto> menus = menuService.getMenusByRestaurantId(restaurantId);
        return ResponseEntity.ok()
                .body(menus);
    }
}