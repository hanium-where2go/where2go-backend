package hanium.where2go.domain.liquor.repository;

import hanium.where2go.domain.category.entity.Category;
import hanium.where2go.domain.liquor.entity.Liquor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LiquorRepository extends JpaRepository<Liquor, Long> {


    @Query("SELECT l FROM Liquor l WHERE l.liquorName IN :liquorName")
    List<Liquor> findByLiquorNameIn(@Param("liquorName") List<String> liquorNames);
}
