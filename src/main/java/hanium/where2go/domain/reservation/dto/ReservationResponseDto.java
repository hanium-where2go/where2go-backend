package hanium.where2go.domain.reservation.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ReservationResponseDto {


    private ReservationStatus status;      //예약 상태 -> Enum
    private String rejection; // 거절 사유
    private Long confirmNum; // 확인 번호
    private Long reservationId;


}
