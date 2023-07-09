package hanium.where2go.domain.reservation.dto;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ReservationRequestDto {


    private Long restaurandId;
    private Integer numberOfPeople; // 사람 수
    private LocalDateTime reservationTime; // 몇 분 안에 올건지
    private String content; // 요청 사항

}
