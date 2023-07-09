package hanium.where2go.domain.reservation.controller;

import hanium.where2go.domain.reservation.dto.ReservationRequestDto;
import hanium.where2go.domain.reservation.dto.ReservationResponseDto;
import hanium.where2go.domain.reservation.dto.ReservationStatus;
import hanium.where2go.domain.reservation.entity.Reservation;
import hanium.where2go.domain.reservation.service.ReservationService;
import hanium.where2go.domain.restaurant.entity.Restaurant;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
public class ReservationController {

    @Autowired
    private final ReservationService reservationService;

    @PostMapping(value = "/reservation")  // 예약을 받아주는 것
    public ResponseEntity<ReservationResponseDto> create(@RequestBody ReservationRequestDto requestDto) {
        // ReservationRequestDto에서 필요한 데이터 추출
        Long restaurantId = requestDto.getRestaurant_id();
        Integer numberOfPeople = requestDto.getNumberOfPeople();
        LocalDateTime reservationTime = requestDto.getReservationTime();
        String content = requestDto.getContent();

        // 예약 생성을 위한 Reservation 엔티티 생성
        Reservation reservation = Reservation.builder()
                .restaurant(Restaurant.builder().id(restaurantId).build())
                .numberOfPeople(numberOfPeople)
                .reservationTime(reservationTime)
                .content(content)
                .status(ReservationStatus.PENDING.name())
                .rejection(null)
                .build();

        // 예약 저장
        reservationService.saveReservation(reservation);

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