package hanium.where2go.domain.customer.repository;

import hanium.where2go.domain.customer.entity.Customer;
import hanium.where2go.global.oauth2.SocialType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByEmail(String email);

    Optional<Customer> findByNameAndPhoneNumber(String name, String email);

    Optional<Customer> findBySocialTypeAndSocialId(SocialType socialType, String socialId);

}
