package hanium.where2go.domain.restaurant.service;

import hanium.where2go.domain.category.entity.Category;
import hanium.where2go.domain.liquor.entity.Liquor;
import hanium.where2go.domain.reservation.entity.Reservation;
import hanium.where2go.domain.reservation.entity.ReservationStatus;
import hanium.where2go.domain.restaurant.dto.ReservationDto;
import hanium.where2go.domain.restaurant.dto.RestaurantDto;
import hanium.where2go.domain.restaurant.entity.Restaurant;
import hanium.where2go.domain.restaurant.entity.RestaurantCategory;
import hanium.where2go.domain.restaurant.entity.RestaurantLiquor;
import hanium.where2go.domain.restaurant.repository.ReservationRepository;
import hanium.where2go.domain.restaurant.repository.RestaurantRepository;
import hanium.where2go.domain.restaurant.repository.ReviewRepository;
import hanium.where2go.global.response.BaseException;
import hanium.where2go.global.response.ExceptionCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;


@Service
@Transactional
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final SimpMessagingTemplate messagingTemplate;
    private final RestaurantRepository restaurantRepository;

    private static final String NUMBERS = "0123456789";
    private static final Random random = new Random();

    // 주어진 길이만큼의 랜덤 숫자를 생성하는 메서드
    private String generateRandomNumber(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(NUMBERS.length());
            char randomChar = NUMBERS.charAt(randomIndex);
            sb.append(randomChar);
        }
        return sb.toString();
    }

    // 예약 생성
    public void processReservation(Long restaurantId, ReservationDto.ReservationRequestDto reservationRequestDto) {
        // 먼저 사용자의 예약 요청 내용을 엔티티에 저장합니다.
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new BaseException(ExceptionCode.RESTAURANT_NOT_FOUND));

        Reservation reservation = Reservation.builder()
                .restaurant(restaurant)
                .numberOfPeople(reservationRequestDto.getNumberOfPeople())
                .reservationTime(reservationRequestDto.getReservation_time())
                .content(reservationRequestDto.getContent())
                .status(ReservationStatus.PENDING) // 예약 상태를 PENDING으로 설정
                .build();

        reservationRepository.save(reservation);

        // 예약 정보를 클라이언트에게 전송 (WebSocket을 사용)
        // topic 을 구독한 사장님에게 전송
        String message = "새로운 예약 요청이 도착했습니다!";
        messagingTemplate.convertAndSend("/topic/reservation", message);
    }

    public void updateReservationStatus(Long reservationId, ReservationStatus status) {
        // reservationId를 사용하여 예약을 조회합니다.
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new BaseException(ExceptionCode.RESERVATION_NOT_FOUND));

        // status 값에 따라 예약 상태를 업데이트합니다.
        reservation.setStatus(status);

        // 예약 정보를 업데이트합니다.
        reservationRepository.save(reservation);

        // 예약이 완료된 경우 랜덤 예약 번호를 클라이언트에게 전송
        if (status == ReservationStatus.COMPLETED) {
            String reservationNumber = generateRandomNumber(2);
            reservation.setReservationNumber(reservationNumber);

            String successMessage = "예약이 완료되었습니다. 예약 번호: " + reservationNumber;
            messagingTemplate.convertAndSend("/topic/reservation", successMessage);
        }
    }
}




