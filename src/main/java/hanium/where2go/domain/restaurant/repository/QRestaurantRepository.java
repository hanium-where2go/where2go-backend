package hanium.where2go.domain.restaurant.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import hanium.where2go.domain.restaurant.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static hanium.where2go.domain.restaurant.entity.QRestaurant.restaurant;

@Repository
@RequiredArgsConstructor
public class QRestaurantRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public List<Restaurant> findRestaurantsByKeyword(String keyword) {
        return jpaQueryFactory.selectFrom(restaurant)
                .where(restaurant.address.jibunAddr.like("%"+keyword+"%")
                        .or(restaurant.address.roadAddr.contains("%"+keyword+"%"))
                        .or(restaurant.restaurantName.contains("%"+keyword+"%"))
                ).fetch();
    }

    // Todo 좌표 기준 반경 n km 이내 restaurant 조회
//    public List<Restaurant> findRestaurantsByCoords(String longitude, String latitude) {
//        return jpaQueryFactory.selectFrom(restaurant)
//                .where()
//    }

}
