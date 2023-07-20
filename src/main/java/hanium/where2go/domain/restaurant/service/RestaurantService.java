package hanium.where2go.domain.restaurant.service;

import hanium.where2go.domain.restaurant.dto.InformationResponseDto;
import hanium.where2go.domain.restaurant.entity.Restaurant;
import hanium.where2go.domain.restaurant.repository.RestaurantRepository;
import hanium.where2go.global.response.BaseException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    public InformationResponseDto getInformation(Long restaurantId){
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new BaseException(404,"cannot find restaurantId"));

        // Restaurant 객체에서 필요한 정보를 가져와서 InformationResponseDto 객체를 생성하고 반환합니다.
        InformationResponseDto informationResponseDto = new InformationResponseDto(
                restaurant.getLocation(),
                restaurant.getDescription(),
                restaurant.getTel(),
                restaurant.getParkingLot()
        );

        return informationResponseDto;
    }
}
