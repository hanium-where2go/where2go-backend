package hanium.where2go.domain.reservation.repository;

import hanium.where2go.domain.customer.entity.Customer;
import hanium.where2go.domain.reservation.entity.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    @Query("select r from Reservation r join fetch r.restaurant rs where r.customer = :customer and r.status = :status")
    Page<Reservation> findReservation(@Param("customer") Customer customer, @Param("status") String status, Pageable pageable);

    @Query("select r from Reservation r join fetch r.restaurant rs where r.customer = :customer and r.createdAt >= :date and r.status = :status")
    Page<Reservation> findReservationByDate(@Param("customer") Customer customer, @Param("date") LocalDateTime date, @Param("status") String status, Pageable pageable);
}
