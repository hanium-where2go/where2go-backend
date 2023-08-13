package hanium.where2go.domain.restaurant.repository;

import hanium.where2go.domain.restaurant.entity.MenuBoard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuBoardRepository extends JpaRepository<MenuBoard,Long> {
}
