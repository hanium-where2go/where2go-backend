package hanium.where2go.domain.reservation.repository;


import hanium.where2go.domain.reservation.entity.Reservation;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

}
