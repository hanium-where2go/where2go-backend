package hanium.where2go.domain.owner.repository;

import hanium.where2go.domain.owner.entity.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OwnerRepository extends JpaRepository<Owner, Long> {
}
