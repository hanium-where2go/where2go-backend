package hanium.where2go.domain.restaurant.repository;

import hanium.where2go.domain.restaurant.entity.RestaurantCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantCategoryRepository extends JpaRepository<RestaurantCategory,Long> {
}
