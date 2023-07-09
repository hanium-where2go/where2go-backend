package hanium.where2go.domain.reservation.repository;


import hanium.where2go.domain.reservation.entity.Reservation;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReservationRepository {

    private final EntityManager em;

    public  void save(Reservation reservation){
        if(reservation.getId() == null){ // 완전히 새로 생성하는 객체라는 의미이다
            em.persist(reservation);
        }else{
            em.merge(reservation); // update 와 비슷하다
        }
    }

    public Reservation findone(Long id){
        return em.find(Reservation.class,id);
    }

    public List<Reservation> findAll(){
        return em.createQuery("select i from Reservation i", Reservation.class)
                .getResultList();
    }

}
