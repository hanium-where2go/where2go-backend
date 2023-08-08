package hanium.where2go.domain.category.repository;

import hanium.where2go.domain.category.entity.Category;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByCategoryName(String categoryName);

    @Query("SELECT c FROM Category c WHERE c.categoryName IN :categoryNames")
    List<Category> findByCategoryNameIn(@Param("categoryNames") List<String> categoryNames);
}
