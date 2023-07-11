package hanium.where2go.domain.restaurant.entity;


import hanium.where2go.domain.User;
import hanium.where2go.domain.reservation.dto.ReservationStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@Table(name = "owner")
@NoArgsConstructor
@AllArgsConstructor
public class Owner extends User {

    public String businessRegistration;

    @Enumerated(EnumType.STRING)
    private ReservationStatus reservationStatus;

    public String rejectionReason; // 거절 사유 입력



}
