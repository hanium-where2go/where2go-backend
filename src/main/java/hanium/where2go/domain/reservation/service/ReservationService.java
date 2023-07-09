package hanium.where2go.domain.reservation.service;

import hanium.where2go.domain.reservation.entity.Reservation;
import hanium.where2go.domain.reservation.repository.ReservationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ReservationService {

    @Autowired
    private final ReservationRepository reservationRepository;

   @Transactional
   public void saveReservation(Reservation reservation){
       reservationRepository.save(reservation);
   }

    public List<Reservation> findItems(){
       return reservationRepository.findAll();
    }

    public  Reservation findOne(Long reservationId)
    {
        return reservationRepository.findone(reservationId);
    }
}
