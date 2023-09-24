package hanium.where2go.domain.restaurant.service;

import hanium.where2go.domain.category.entity.Category;
import hanium.where2go.domain.customer.entity.Customer;
import hanium.where2go.domain.customer.repository.CustomerRepository;
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
import hanium.where2go.global.redis.RedisUtil;
import hanium.where2go.global.response.BaseException;
import hanium.where2go.global.response.ExceptionCode;
import jakarta.transaction.Transactional;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;




@Service
@Transactional
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final SimpMessagingTemplate messagingTemplate;
    private final RestaurantRepository restaurantRepository;
    private final CustomerRepository customerRepository;
    private final RedisUtil redisUtil;



    private String generateRandomUUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
    // 예약 생성
    public ReservationDto.ReservationResponseDto processReservation(Long restaurantId,Long customerId, ReservationDto.ReservationRequestDto reservationRequestDto) {

        String restaurantKey = String.valueOf(restaurantId);
        if (!(redisUtil.hasKey(restaurantKey))) {
            throw new BaseException(ExceptionCode.CANNOT_FIND_RESTAURANT_KEY);
        }

        String storeStatus = (String) redisUtil.get(restaurantKey+":storeStatus");
        if (!("OPEN".equals(storeStatus))) {
            throw new BaseException(ExceptionCode.STORE_CLOSED);
        }

        // 먼저 사용자의 예약 요청 내용을 엔티티에 저장합니다.
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new BaseException(ExceptionCode.RESTAURANT_NOT_FOUND));

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new BaseException(ExceptionCode.CUSTOMER_NOT_FOUND));

        Reservation reservation = Reservation.builder()
                .restaurant(restaurant)
                .customer(customer)
                .numberOfPeople(reservationRequestDto.getNumberOfPeople())
                .reservationTime(reservationRequestDto.getReservation_time())
                .content(reservationRequestDto.getContent())
                .status(ReservationStatus.PENDING) // 예약 상태를 PENDING으로 설정
                .build();

        reservationRepository.save(reservation);

        // 예약 정보를 클라이언트에게 전송 (WebSocket을 사용)
        // /sub/reservation 구독하면 사장님. 고객 둘다에게 전달 가능
            String message = "새로운 예약 요청이 도착했습니다";
            messagingTemplate.convertAndSend("/sub/reservation", message);

            return ReservationDto.ReservationResponseDto.builder()
                    .reservationId(reservation.getId())
                    .build();
        }


    public void updateReservationStatus(Long restaurantId,Long reservationId, ReservationDto.updateReservationStatus updateReservationStatus) {
        // reservationId를 사용하여 예약을 조회합니다.
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new BaseException(ExceptionCode.RESERVATION_NOT_FOUND));

        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new BaseException(ExceptionCode.RESTAURANT_NOT_FOUND));

        ReservationStatus newStatus = updateReservationStatus.getReservationStatus();
        // status 값에 따라 예약 상태를 업데이트합니다.
        reservation.setStatus(newStatus);
            reservation.setRejection(updateReservationStatus.getRejection());


        // 예약 정보를 업데이트합니다.
        reservationRepository.save(reservation);

        String restaurantKey = String.valueOf(restaurantId);
        if (!(redisUtil.hasKey(restaurantKey))) {
            throw new BaseException(ExceptionCode.CANNOT_FIND_RESTAURANT_KEY);
        }


        // 잔송 자체를 메서드로 따로 구현
        // 예약이 완료된 경우 랜덤 예약 번호를 클라이언트에게 전송
        if (newStatus == ReservationStatus.COMPLETED) {
            String reservationNumber = generateRandomUUID();
            reservation.setReservationNumber(reservationNumber);

            String seatCountStr = (String) redisUtil.get(restaurantKey);
            int currentSeatCount = Integer.parseInt(seatCountStr);
            int updatedSeatCount = currentSeatCount - reservation.getNumberOfPeople();
            redisUtil.set(restaurantKey, Integer.toString(updatedSeatCount),2*60*60*1000);

            String successMessage = "예약이 완료되었습니다. 예약 번호: " + reservationNumber +" "+ "실시간 좌석 수: " + redisUtil.get(restaurantKey);
            messagingTemplate.convertAndSend("/sub/reservation", successMessage );
        }
        else {
            String failMessage = "예약이 거절되었습니다. " + updateReservationStatus.getRejection().getDescription() + " " + "실시간 좌석 수 " + redisUtil.get(restaurantKey);
            messagingTemplate.convertAndSend("/sub/reservation", failMessage);
        }
        }

    public ReservationDto.ReservationInformationResponseDto searchReservation(Long reservationId){

        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new BaseException(ExceptionCode.RESERVATION_NOT_FOUND));

        return ReservationDto.ReservationInformationResponseDto.builder()
                .restaurantId(reservation.getRestaurant().getRestaurantId())
                .restaurantName(reservation.getRestaurant().getRestaurantName())
                .reservationDate(LocalDate.from(reservation.getCreatedAt()))
                .numberOfPeople(reservation.getNumberOfPeople())
                .reservationTime(LocalTime.ofSecondOfDay(reservation.getReservationTime()))
                .arrivingTime(LocalTime.ofSecondOfDay(reservation.getReservationTime()))
                .content(reservation.getContent())
                .phoneNumber(reservation.getCustomer().getPhoneNumber())
                .username(reservation.getCustomer().getNickname())
                .reservationStatus(reservation.getStatus())
                .build();
    }
}




