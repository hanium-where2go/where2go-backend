package hanium.where2go.domain.restaurant.dto;

import hanium.where2go.domain.restaurant.entity.Event;
import hanium.where2go.domain.restaurant.entity.Restaurant;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;

@Component
public class RestaurantMapper {
    public RestaurantDto.CommonInformationResponseDto restaurantToRestaurantCommonInformationDto(Restaurant restaurant) {
        return RestaurantDto.CommonInformationResponseDto.builder()
                .restaurantName(restaurant.getRestaurantName())
                .restaurantImage(restaurant.getRestaurantImage())
                .title(getLatestEvent(restaurant.getEvents()))
                .description(restaurant.getDescription())
                .responseAvg(restaurant.getResponseAvg())
                .rateAvg(restaurant.getRateAvg())
                .seat(restaurant.getSeat())
                .build();
    }

    private String getLatestEvent(List<Event> events) {
        Event latestEvent = events.stream()
                .max(Comparator.comparing(Event::getEndDate))
                .orElse(null);
        return latestEvent == null ? "현재 진행 중인 이벤트가 없습니다." : latestEvent.getTitle();
    }
}
