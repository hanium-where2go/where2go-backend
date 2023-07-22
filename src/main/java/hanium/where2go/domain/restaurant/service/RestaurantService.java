package hanium.where2go.domain.restaurant.service;

import hanium.where2go.domain.reservation.entity.Review;
import hanium.where2go.domain.restaurant.dto.CommonInformationResponseDto;
import hanium.where2go.domain.restaurant.dto.InformationResponseDto;
import hanium.where2go.domain.restaurant.entity.Event;
import hanium.where2go.domain.restaurant.entity.Restaurant;
import hanium.where2go.domain.restaurant.repository.RestaurantRepository;
import hanium.where2go.global.response.BaseException;
import hanium.where2go.global.response.ExceptionCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    public InformationResponseDto getInformation(Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new BaseException(ExceptionCode.RESTAURANT_NOT_FOUND));

        // Restaurant 객체에서 필요한 정보를 가져와서 InformationResponseDto 객체를 생성하고 반환합니다.
        InformationResponseDto informationResponseDto = new InformationResponseDto(
                restaurant.getLocation(),
                restaurant.getDescription(),
                restaurant.getTel(),
                restaurant.getParkingLot()
        );

        return informationResponseDto;
    }

    public CommonInformationResponseDto getCommonInformation(Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new BaseException(ExceptionCode.RESTAURANT_NOT_FOUND));

        // 현재 날짜를 기준으로 필터링할 때 사용할 날짜를 가져옵니다.
        LocalDateTime currentDate = LocalDateTime.now();


        // 해당 기간에 해당하는 이벤트만 필터링하여 가져옵니다.
        List<Event> filteredEvents = restaurant.getEvents().stream()
                .filter(event -> event.getEndDate().isAfter(currentDate))
                .collect(Collectors.toList());

        // 최근 이벤트가 존재하는 경우에만 title 설정, 그렇지 않은 경우 기본 제목 설정
        String title;

        // 평점을 계산하기 위한것
        double rateAvg = calculateRate(restaurant);


        if (!filteredEvents.isEmpty()) {
            Event latestEvent = filteredEvents.stream()
                    .max(Comparator.comparing(Event::getEndDate))
                    .orElse(null);
            title = latestEvent.getTitle();
        } else {
            title = "해당 날짜에 이벤트가 없습니다."; // 기본 제목 설정
        }

        CommonInformationResponseDto commonInformationResponseDto = new CommonInformationResponseDto(
                restaurant.getRestaurantName(),
                restaurant.getRestaurantImage(),
                title,
                restaurant.getDescription(),
                restaurant.getResponseAvg(),
                rateAvg,
                restaurant.getSeat()
        );
        return commonInformationResponseDto;
    }

    private double calculateRate(Restaurant restaurant) {
        List<Review> reviews = restaurant.getReview();
        if (reviews.isEmpty()) {
            return 0.0; // 리뷰가 없으면 0을 반환합니다.
        }

        double sumOfRatings = reviews.stream()
                .mapToDouble(Review::getRate)
                .sum();

        return sumOfRatings / reviews.size();
    }

}

