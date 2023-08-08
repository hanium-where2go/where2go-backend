package hanium.where2go.domain.liquor.repository;

import hanium.where2go.domain.category.entity.Category;
import hanium.where2go.domain.liquor.entity.Liquor;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LiquorRepository extends JpaRepository<Liquor, Long> {



    @Query("SELECT l from Liquor l WHERE l.liquorName IN :liquorNames")
    List<Liquor> findByLiquorNameIn(@Param("liquorNames") List<String> liquorNames);
}
