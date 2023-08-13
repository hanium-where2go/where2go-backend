package hanium.where2go.domain.restaurant.repository;

import hanium.where2go.domain.restaurant.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu,Long> {
}
