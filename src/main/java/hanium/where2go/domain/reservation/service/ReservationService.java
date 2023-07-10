package hanium.where2go.domain.reservation.service;

import hanium.where2go.domain.reservation.dto.ReservationRequestDto;
import hanium.where2go.domain.reservation.dto.ReservationResponseDto;
import hanium.where2go.domain.reservation.dto.ReservationStatus;
import hanium.where2go.domain.reservation.entity.Reservation;
import hanium.where2go.domain.reservation.repository.ReservationRepository;
import hanium.where2go.domain.restaurant.entity.Restaurant;
import hanium.where2go.global.response.BaseException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ReservationService {

    @Autowired
    private final ReservationRepository reservationRepository;

    @Transactional
    public void saveReservation(Reservation reservation) {
        reservationRepository.save(reservation);
    }

    public ResponseEntity<ReservationResponseDto> create(ReservationRequestDto requestDto) {
        // ReservationRequestDto에서 필요한 데이터 추출


        // 예약 생성을 위한 Reservation 엔티티 생성
        Reservation reservation =  Reservation.builder()
                .restaurant(Restaurant.builder()
                        .restaurantId(requestDto.getRestaurandId())  // 직접 restaurantId에 값을 할당
                        .build())
                .numberOfPeople(requestDto.getNumberOfPeople())
                .reservationTime(requestDto.getReservationTime())
                .content(requestDto.getContent())
                .status(ReservationStatus.PENDING.name())
                .rejection(null)
                .build();

        reservationRepository.save(reservation);
        // 예약 저장


        // 응답 생성
        ReservationResponseDto responseDto = new ReservationResponseDto();
        responseDto.setStatus(ReservationStatus.PENDING);
        responseDto.setRejection(null);
        responseDto.setReservationId(reservation.getId());
        responseDto.setConfirmNum(reservation.getConfirmNum());

        // 응답 반환
        return ResponseEntity.ok(responseDto);
    }
}
