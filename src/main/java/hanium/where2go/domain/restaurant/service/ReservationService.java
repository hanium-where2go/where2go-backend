package hanium.where2go.domain.restaurant.service;

import hanium.where2go.domain.customer.entity.Customer;
import hanium.where2go.domain.customer.repository.CustomerRepository;
import hanium.where2go.domain.reservation.entity.Reservation;
import hanium.where2go.domain.restaurant.dto.ReservationDto;
import hanium.where2go.domain.restaurant.entity.Restaurant;
import hanium.where2go.domain.restaurant.repository.ReservationRepository;
import hanium.where2go.domain.restaurant.repository.RestaurantRepository;
import hanium.where2go.global.response.BaseException;
import hanium.where2go.global.response.ExceptionCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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


        Reservation reservation = Reservation.builder()
                .restaurant(restaurant)
                .numberOfPeople(reservationRequestDto.getNumberOfPeople())
                .reservationTime(reservationRequestDto.getReservation_time())
                .content(reservationRequestDto.getContent())
                .build();

        reservationRepository.save(reservation);


    }
}
