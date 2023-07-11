package hanium.where2go.domain.reservation.service;

import hanium.where2go.domain.reservation.dto.ReservationRequestDto;
import hanium.where2go.domain.reservation.dto.ReservationResponseDto;
import hanium.where2go.domain.reservation.dto.ReservationStatus;
import hanium.where2go.domain.reservation.entity.Reservation;
import hanium.where2go.domain.reservation.repository.ReservationRepository;
import hanium.where2go.domain.restaurant.entity.Owner;
import hanium.where2go.domain.restaurant.entity.Restaurant;
import hanium.where2go.global.jwt.JwtProvider;
import hanium.where2go.global.response.BaseException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
@Transactional
@RequiredArgsConstructor
public class ReservationService {

    @Autowired
    private final ReservationRepository reservationRepository;

    private final JwtProvider jwtProvider;

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
                .status(ReservationStatus.CONFIRMED.name()) // 이 부분을 나중에 사장님의 상태로 바꾸어 주어야함. jwt 이용해야함
                .build();


        // 예약 저장
        reservationRepository.save(reservation);

        if (reservation.getStatus().equals(ReservationStatus.CONFIRMED.name())) {
            ReservationResponseDto responseDto = new ReservationResponseDto();
            responseDto.setStatus(ReservationStatus.CONFIRMED);
            responseDto.setReservationId(reservation.getId());
            responseDto.setConfirmNum(reservation.getConfirmNum());
            return ResponseEntity.ok(responseDto);
        } else {
            String rejectionReason = reservation.getOwner().rejectionReason;
            throw new BaseException(404,rejectionReason);
        }
    }

}


