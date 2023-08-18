package hanium.where2go.domain.restaurant.repository;


import hanium.where2go.domain.restaurant.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event,Long> {
}
