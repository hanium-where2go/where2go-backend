package hanium.where2go.domain.restaurant.service;

import hanium.where2go.domain.restaurant.dto.RestaurantDto;
import hanium.where2go.domain.restaurant.dto.RestaurantMapper;
import hanium.where2go.domain.restaurant.entity.Restaurant;
import hanium.where2go.domain.restaurant.repository.QRestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RestaurantAddressServiceImpl implements RestaurantAddressService{

    private final QRestaurantRepository qRestaurantRepository;
    private final RestaurantMapper restaurantMapper;

    @Override
    public List<RestaurantDto.CommonInformationResponseDto> findRestaurantsByKeyword(String keyword) {
        List<Restaurant> findRestaurants = qRestaurantRepository.findRestaurantsByKeyword(keyword);

        return findRestaurants.stream().map(
                restaurant -> restaurantMapper.restaurantToRestaurantCommonInformationDto(restaurant)
        ).collect(Collectors.toList());
    }

    @Override
    public void findRestaurantsByCoords(String longitude, String latitude) {
        // Todo 좌표로 반경 n km 이내 restaurant 조회
    }
}
