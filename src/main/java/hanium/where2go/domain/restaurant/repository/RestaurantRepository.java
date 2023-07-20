package hanium.where2go.domain.restaurant.repository;

import hanium.where2go.domain.restaurant.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant,Long>{
}


