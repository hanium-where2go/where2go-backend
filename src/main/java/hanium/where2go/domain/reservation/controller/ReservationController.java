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

    @PostMapping("/reservation")
    public ResponseEntity<ReservationResponseDto> create(@RequestBody ReservationRequestDto requestDto) {
        ReservationResponseDto reservationResponseDto = reservationService.create(requestDto).getBody();
        return ResponseEntity.ok(reservationResponseDto);
    }
}