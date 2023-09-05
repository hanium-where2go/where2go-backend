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
    private final RestaurantRepository restaurantRepository;
    private final CustomerRepository customerRepository;

    public ReservationDto.ReservationResponseDto makeReservation(Long restaurantId, Long customerId,ReservationDto.ReservationRequestDto reservationRequestDto){
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new BaseException(ExceptionCode.RESTAURANT_NOT_FOUND));

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new BaseException(ExceptionCode.CUSTOMER_NOT_FOUND));


        // 예약 엔티티 생성
        Reservation reservation = Reservation.builder()
                .restaurant(restaurant)
                .customer(customer)
                .numberOfPeople(reservationRequestDto.getNumberOfPeople())
                .reservationTime(reservationRequestDto.getReservation_time())
                .content(reservationRequestDto.getContent())
                .status(ReservationStatus.PENDING) // 기본 상태는 PENDING으로 설정
                .build();

        reservationRepository.save(reservation);

        // 예약 응답 DTO 생성
        ReservationDto.ReservationResponseDto responseDto;

        // 예약 상태에 따라 응답 DTO 생성
        if (예약을 승인한 경우) {
            // COMPLETED 상태와 랜덤 예약 번호 생성
            String status = "예약 완료";
            String confirmationNumber = generateConfirmationNumber(); // 랜덤 번호 생성
            reservation.setStatus(ReservationStatus.COMPLETED); // 예약 상태를 COMPLETED로 변경
            reservation.setConfirmationNumber(confirmationNumber); // 랜덤 예약 번호 저장

            // 클라이언트에게 보낼 응답 DTO 생성
            responseDto = ReservationDto.ReservationResponseDto.createCompletedResponse(confirmationNumber, reservation.getId());
        } else {
            // 거절 사유와 REFUSED 상태 저장
            String status = "예약 불가";
            String rejectionReason = "거절 사유 입력"; // 사장님이 거절한 이유
            reservation.setStatus(ReservationStatus.REFUSED); // 예약 상태를 REFUSED로 변경
            reservation.setRejection(rejectionReason); // 거절 사유 저장

            // 클라이언트에게 보낼 응답 DTO 생성
            responseDto = ReservationDto.ReservationResponseDto.createRefusedResponse(rejectionReason, reservation.getId());
        }

        // 엔티티 업데이트
        reservationRepository.save(reservation);

        return responseDto;
    }

    // 랜덤 예약 번호 생성 로직 구현
    private String generateConfirmationNumber() {
        // 랜덤 번호 생성 로직을 추가하세요.
        // 예를 들어, UUID를 사용하여 고유한 번호를 생성할 수 있습니다.
        return UUID.randomUUID().toString();
    }
}



    }
}
