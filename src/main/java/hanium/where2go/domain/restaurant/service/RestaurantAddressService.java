package hanium.where2go.domain.restaurant.service;

import hanium.where2go.domain.restaurant.dto.RestaurantDto;

import java.util.List;

public interface RestaurantAddressService {
    // 주소 기반 레스토랑 조회
    List<RestaurantDto.CommonInformationResponseDto> findRestaurantsByKeyword(String keyword);

    // Todo 좌표로 레스토랑 조회
    void findRestaurantsByCoords(String longitude, String latitude);
}