package hanium.where2go.domain.restaurant.service;

import hanium.where2go.domain.category.entity.Category;
import hanium.where2go.domain.category.repository.CategoryRepository;
import hanium.where2go.domain.liquor.entity.Liquor;
import hanium.where2go.domain.liquor.repository.LiquorRepository;
import hanium.where2go.domain.reservation.entity.Review;
import hanium.where2go.domain.restaurant.dto.*;
import hanium.where2go.domain.restaurant.entity.*;
import hanium.where2go.domain.restaurant.repository.*;
import hanium.where2go.global.response.BaseException;
import hanium.where2go.global.response.ExceptionCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final CategoryRepository categoryRepository;
    private final LiquorRepository liquorRepository;
    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;

     // 레스토랑 단일 정보 얻기
    public RestaurantDto.InformationResponseDto getInformation(Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new BaseException(ExceptionCode.RESTAURANT_NOT_FOUND));

        // Restaurant 객체에서 필요한 정보를 가져와서 InformationResponseDto 객체를 생성하고 반환합니다.
        RestaurantDto.InformationResponseDto informationResponseDto = new RestaurantDto.InformationResponseDto(
                restaurant.getAddress().getRoadAddr(),
                restaurant.getDescription(),
                restaurant.getTel(),
                restaurant.getParkingLot()
        );

        return informationResponseDto;
    }

     // 레스토랑 공통 정보 얻기
    public RestaurantDto.CommonInformationResponseDto getCommonInformation(Long restaurantId) {
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

        RestaurantDto.CommonInformationResponseDto commonInformationResponseDto = new RestaurantDto.CommonInformationResponseDto(
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

     // 해시태그의 평균 계산
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


    // 레스토랑 정보 등록
    @Transactional
    public RestaurantDto.RestaurantEnrollResponseDto enrollRestaurant(RestaurantDto.RestaurantEnrollRequestDto restaurantEnrollDto){

        Address address = Address.builder()
                .roadAddr(restaurantEnrollDto.getAddressDto().getRoadAddr())
                .jibunAddr(restaurantEnrollDto.getAddressDto().getJibunAddr())
                .latitude(restaurantEnrollDto.getAddressDto().getLatitude())
                .longitude(restaurantEnrollDto.getAddressDto().getLongitude())
                .build();

        Restaurant restaurant = Restaurant.builder()
                .restaurantName(restaurantEnrollDto.getRestaurantName())
                .start_time(restaurantEnrollDto.getStartTime())
                .end_time(restaurantEnrollDto.getEndTime())
                .closed_day(restaurantEnrollDto.getClosedDay())
                .tel(restaurantEnrollDto.getTel())
                .total_seat(restaurantEnrollDto.getTotalSeat())
                .onetime_Seat(restaurantEnrollDto.getOnetimeSeat())
                .parkingLot(restaurantEnrollDto.getParkingLot())
                .address(address)
                .build();

        // 요청된 레스토랑의 카테고리를 추출한다
        List<Category> categories = categoryRepository.findByCategoryNameIn(restaurantEnrollDto.getCategoryNames());
        restaurant.setRestaurantCategories(categories.stream()
                .map(category -> new RestaurantCategory(restaurant, category))
                .collect(Collectors.toList()));

        // 요청 된 레스토랑의 주종을 추출한다
        List<Liquor> liquors = liquorRepository.findByLiquorNameIn(restaurantEnrollDto.getLiquorNames());
        restaurant.setRestaurantLiquors(liquors.stream()
                .map(liquor -> new RestaurantLiquor(restaurant, liquor))
                .collect(Collectors.toList()));

        // 레스토랑 저장해주기
        Restaurant savedRestaurant = restaurantRepository.save(restaurant);

        return new RestaurantDto.RestaurantEnrollResponseDto(savedRestaurant.getRestaurantId(), savedRestaurant.getRestaurantName());

    }


    // 레스토랑 정보 업데이트
    public RestaurantDto.RestaurantUpdateResponseDto updateRestaurantInfo(Long restaurantId, RestaurantDto.RestaurantUpdateRequestDto restaurantUpdateRequestDto) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new BaseException(ExceptionCode.RESTAURANT_NOT_FOUND));



        restaurant.update(restaurantUpdateRequestDto);

        List<Category> updatedCategories = categoryRepository.findByCategoryNameIn(restaurantUpdateRequestDto.getCategoryNames());
        updateCategories(restaurant, updatedCategories);

        List<Liquor> updatedLiquors = liquorRepository.findByLiquorNameIn(restaurantUpdateRequestDto.getLiquorNames());
        updateLiquors(restaurant, updatedLiquors);

        Restaurant savedRestaurant = restaurantRepository.save(restaurant);

        List<String> categories = savedRestaurant.getRestaurantCategories().stream()
                .map(RestaurantCategory -> RestaurantCategory.getCategory().getCategoryName())
                .collect(Collectors.toList());

        List<String> liquors = savedRestaurant.getRestaurantLiquors().stream()
                .map(RestaurantLiquor -> RestaurantLiquor.getLiquor().getLiquorName())
                .collect(Collectors.toList());

        return RestaurantDto.RestaurantUpdateResponseDto.builder()
                .restaurantId(savedRestaurant.getRestaurantId())
                .restaurantName(savedRestaurant.getRestaurantName())
                .addressDto(addressMapper.addressToAddressDto(savedRestaurant.getAddress()))
                .startTime(savedRestaurant.getStart_time())
                .endTime(savedRestaurant.getEnd_time())
                .closedDay(savedRestaurant.getClosed_day())
                .tel(savedRestaurant.getTel())
                .totalSeat(savedRestaurant.getTotal_seat())
                .onetimeSeat(savedRestaurant.getOnetime_Seat())
                .parkingLot(savedRestaurant.getParkingLot())
                .categoryNames(categories)
                .liquorNames(liquors)
                .build();

    }


    private void updateCategories(Restaurant restaurant, List<Category> updatedCategories) {
        List<RestaurantCategory> existingCategories = new ArrayList<>(restaurant.getRestaurantCategories());
        Iterator<RestaurantCategory> categoryIterator = existingCategories.iterator();
        while (categoryIterator.hasNext()) {
            RestaurantCategory existingCategory = categoryIterator.next();
            if (!updatedCategories.contains(existingCategory.getCategory())) { // 업데이트 카테고리에 원래 있던 카테고리가 없다면 원래 있던 카테고리 삭제
                restaurant.getRestaurantCategories().remove(existingCategory);
            }
        }
        for (Category category : updatedCategories) {
            if (restaurant.getRestaurantCategories().stream().noneMatch(rc -> rc.getCategory().equals(category))) { // updateCategory 에 원래의 내용이 없다면
                restaurant.getRestaurantCategories().add(new RestaurantCategory(restaurant, category)); // 새로 추가
            }
        }
    }

    private void updateLiquors(Restaurant restaurant, List<Liquor> updatedLiquors) {
        List<RestaurantLiquor> existingLiquors = new ArrayList<>(restaurant.getRestaurantLiquors());
        Iterator<RestaurantLiquor> liquorIterator = existingLiquors.iterator();
        while (liquorIterator.hasNext()) {
            RestaurantLiquor existingLiquor = liquorIterator.next();
            if (!updatedLiquors.contains(existingLiquor.getLiquor())) {
                restaurant.getRestaurantLiquors().remove(existingLiquor);
            }
        }
        for (Liquor liquor : updatedLiquors) {
            if (restaurant.getRestaurantLiquors().stream().noneMatch(rl -> rl.getLiquor().equals(liquor))) {
                restaurant.getRestaurantLiquors().add(new RestaurantLiquor(restaurant, liquor));
            }
        }
    }



}

