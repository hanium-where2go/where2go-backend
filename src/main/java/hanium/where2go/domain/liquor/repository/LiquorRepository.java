package hanium.where2go.domain.liquor.repository;

import hanium.where2go.domain.liquor.entity.Liquor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LiquorRepository extends JpaRepository<Liquor, Long> {

}
