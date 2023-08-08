package hanium.where2go.domain.category.repository;

import hanium.where2go.domain.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByCategoryName(String categoryName);

    @Query("SELECT c FROM Category c WHERE c.categoryName IN :categoryName")
    List<Category> findByCategoryNameIn(@Param("categoryName") List<String> categoryNames);

}
