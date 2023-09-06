package hanium.where2go.domain.restaurant.service;

import hanium.where2go.domain.customer.entity.Customer;
import hanium.where2go.domain.customer.repository.CustomerRepository;
import hanium.where2go.domain.reservation.entity.Reservation;
import hanium.where2go.domain.reservation.entity.ReservationStatus;
import hanium.where2go.domain.restaurant.dto.ReservationDto;
import hanium.where2go.domain.restaurant.entity.Restaurant;
import hanium.where2go.domain.restaurant.repository.ReservationRepository;
import hanium.where2go.domain.restaurant.repository.RestaurantRepository;
import hanium.where2go.global.response.BaseException;
import hanium.where2go.global.response.ExceptionCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationDto.ReservationResponseDto approveReservation(Long reservationId) {
        // 예약 승인 동작 처리
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new BaseException(ExceptionCode.RESERVATION_NOT_FOUND));

        reservation.setStatus(ReservationStatus.COMPLETED);
        String confirmationNumber = generateConfirmationNumber(); // 랜덤 번호 생성
        reservation.setConfirmationNumber(confirmationNumber);

        reservationRepository.save(reservation);

        // 클라이언트에게 보낼 응답 DTO 생성
        return ReservationResponseDto.createCompletedData(confirmationNumber, reservationId);
    }

    public ReservationDto.ReservationResponseDto rejectReservation(Long reservationId, String rejectionReason) {
        // 예약 거절 동작 처리
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new BaseException(ExceptionCode.RESERVATION_NOT_FOUND));

        reservation.setStatus(ReservationStatus.REFUSED);
        reservation.setRejection(rejectionReason);

        reservationRepository.save(reservation);

        // 클라이언트에게 보낼 응답 DTO 생성
        return ReservationResponseDto.createRefusedData(rejectionReason, reservationId);
    }

    // 랜덤 예약 번호 생성 로직 구현
    private String generateConfirmationNumber() {
        // 랜덤 번호 생성 로직을 추가하세요.
        // 예를 들어, UUID를 사용하여 고유한 번호를 생성할 수 있습니다.
        return UUID.randomUUID().toString();
    }
}
