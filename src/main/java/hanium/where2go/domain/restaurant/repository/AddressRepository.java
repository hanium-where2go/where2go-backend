package hanium.where2go.domain.restaurant.repository;

import hanium.where2go.domain.restaurant.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
