package hanium.where2go.domain.restaurant.service;

import hanium.where2go.domain.restaurant.dto.RestaurantEventDto;
import hanium.where2go.domain.restaurant.entity.Event;
import hanium.where2go.domain.restaurant.entity.Restaurant;
import hanium.where2go.domain.restaurant.repository.EventRepository;
import hanium.where2go.domain.restaurant.repository.RestaurantRepository;
import hanium.where2go.global.response.BaseException;
import hanium.where2go.global.response.ExceptionCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class EventService {

    private final RestaurantRepository restaurantRepository;
    private final EventRepository eventRepository;

    // 이벤트 등록
    public RestaurantEventDto.EventEnrollResponseDto enrollEvents(Long restaurantId, RestaurantEventDto.EventEnrollRequestDto eventEnrollRequestDto){

        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new BaseException(ExceptionCode.RESTAURANT_NOT_FOUND));

        Event event = Event.builder()
                .restaurant(restaurant)
                .content(eventEnrollRequestDto.getContent())
                .title(eventEnrollRequestDto.getTitle())
                .startDate(eventEnrollRequestDto.getStartDate().atStartOfDay())
                .endDate(eventEnrollRequestDto.getEndDate().atStartOfDay())
                .build();

        eventRepository.save(event);

        return RestaurantEventDto.EventEnrollResponseDto.builder()
                .eventId(event.getId())
                .build();
    }

    // 이벤트 수정
    public RestaurantEventDto.EventUpdateResponseDto updateEvent(Long restaurantId, Long eventId, RestaurantEventDto.EventtUpdateRequestDto eventtUpdateRequestDto){

        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new BaseException(ExceptionCode.RESTAURANT_NOT_FOUND));

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new BaseException(ExceptionCode.RESTAURANT_NOT_FOUND));

        event.update(eventtUpdateRequestDto);

        return RestaurantEventDto.EventUpdateResponseDto.builder()
                .eventId(event.getId())
                .restaurantName(restaurant.getRestaurantName())
                .title(event.getTitle())
                .content(event.getContent())
                .startDate(LocalDate.from(event.getStartDate()))
                .endDate(LocalDate.from(event.getEndDate()))
                .build();

    }
    // 전체 이벤트 조회
    public List<RestaurantEventDto.EventSearchResponseDto> searchEvents(Long restaurantId){
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new BaseException(ExceptionCode.RESTAURANT_NOT_FOUND));

        List<RestaurantEventDto.EventSearchResponseDto> eventSearchResponseDtos = restaurant.getEvents().stream()
                .map(event -> new RestaurantEventDto.EventSearchResponseDto(
                        event.getId(),
                        event.getTitle(),
                        event.getContent(),
                        event.getStartDate().toLocalDate(),
                        event.getEndDate().toLocalDate()
                )).collect(Collectors.toList());

        return eventSearchResponseDtos;
    }

    // 하나의 이벤트 조회
    public RestaurantEventDto.SingleEventSearchResponseDto searchSingleEvent(Long restaurantId, Long eventId){
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new BaseException(ExceptionCode.RESTAURANT_NOT_FOUND));

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new BaseException(ExceptionCode.EVENT_NOT_FOUND));

        return RestaurantEventDto.SingleEventSearchResponseDto.builder()
                .eventId(event.getId())
                .restaurantName(restaurant.getRestaurantName())
                .content(event.getContent())
                .title(event.getTitle())
                .startDate(event.getStartDate().toLocalDate())
                .endDate(event.getEndDate().toLocalDate())
                .build();
    }

    // 이벤트 삭제
    public void deleteEvent(Long restaurantId, Long eventId){
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new BaseException(ExceptionCode.RESTAURANT_NOT_FOUND));

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new BaseException(ExceptionCode.EVENT_NOT_FOUND));

        eventRepository.delete(event);
    }
}
