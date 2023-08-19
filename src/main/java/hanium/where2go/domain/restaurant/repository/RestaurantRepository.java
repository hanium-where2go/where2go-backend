package hanium.where2go.domain.restaurant.repository;

import hanium.where2go.domain.restaurant.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant,Long>{

}


