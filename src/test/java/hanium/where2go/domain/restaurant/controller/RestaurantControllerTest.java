package hanium.where2go.domain.restaurant.controller;

import hanium.where2go.domain.restaurant.dto.InformationResponseDto;
import hanium.where2go.domain.restaurant.entity.Menu;
import hanium.where2go.domain.restaurant.entity.Restaurant;
import hanium.where2go.domain.restaurant.service.RestaurantService;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Transactional
class RestaurantControllerTest {

    @Autowired
    private EntityManager em;

    @Autowired
    private RestaurantService restaurantService;

    @BeforeEach
    void beforeEach() {
        // Set up initial data if needed

//            Restaurant restaurant = Restaurant.builder()
//                    .seat(100)
//
//                    .menuList(new ArrayList<>())
//                    .build();
//
//            em.persist(restaurant);
//
//            Menu menu1 = Menu.builder()
//                    .restaurant(restaurant)
//                    .content("맛있는 치킨")
//                    .name("치킨")
//                    .price(10000)
//                    .imgUrl("test/test1.jpg")
//                    .build();
//
//            Menu menu2 = Menu.builder()
//                    .restaurant(restaurant)
//                    .content("맛있는 피자")
//                    .name("피자")
//                    .price(15000)
//                    .imgUrl("test/test2.jpg")
//                    .build();
//
//            List<Menu> menuList = new ArrayList<>();
//            menuList.add(menu1);
//            menuList.add(menu2);
//
//            restaurant.setMenuList(menuList);
//
//            em.persist(menu1);
//            em.persist(menu2);
    }

//    @Test
//    void 메뉴_가져오기() {
//        // given
//        Long restaurantId = 1L;
//
//        // when
//        Restaurant restaurant = em.find(Restaurant.class, restaurantId);
//        List<Menu> menuList = restaurant.getMenuList();
//
//        // then
//        Assertions.assertThat(menuList.size()).isEqualTo(2);
//        Assertions.assertThat(menuList.get(0).getContent()).isEqualTo("맛있는 치킨");
//        Assertions.assertThat(menuList.get(1).getContent()).isEqualTo("맛있는 피자");
//    }

    @Test
    void getInformation() {
        // given
        Long restaurantId = 1L;
        String location = "강남구 삼성동";
        String description = "맛있는 음식점";
        String tel = "02-785-2392";
        Boolean parkingLot = true;

        Restaurant restaurant = Restaurant.builder()
                .location(location)
                .description(description)
                .tel(tel)
                .parkingLot(parkingLot)
                .build();

        em.persist(restaurant);

        // when
        InformationResponseDto informationResponse = restaurantService.getInformation(restaurantId);

        // then
        Assertions.assertThat(informationResponse.getLocation()).isEqualTo(location);
        Assertions.assertThat(informationResponse.getDescription()).isEqualTo(description);
        Assertions.assertThat(informationResponse.getTel()).isEqualTo(tel);
        Assertions.assertThat(informationResponse.getParkingLot()).isEqualTo(parkingLot);
    }
}