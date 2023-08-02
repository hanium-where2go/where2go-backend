package hanium.where2go.domain.restaurant.repository;

import hanium.where2go.domain.reservation.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review,Long> {


}
