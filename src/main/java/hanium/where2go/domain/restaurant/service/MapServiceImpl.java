package hanium.where2go.domain.restaurant.service;

import hanium.where2go.domain.restaurant.dto.MapDto;
import hanium.where2go.domain.restaurant.entity.Address;
import hanium.where2go.global.utils.MapResponse;
import hanium.where2go.global.utils.MapUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MapServiceImpl implements MapService {

    private final MapUtils mapUtils;

    @Override
    public MapDto.Response getAddrByKeywords(String keywords) {
        // 1. keyword를 통한 geocode 호출
        MapResponse mapResponse = mapUtils.getGeocode(keywords); // POJO

        // 2. 응답 json mapResponseDto 변환 후 리턴
        return createMapResponseDto(mapResponse);
    }

    @Override
    public MapDto.Response getAddrByCoords(String longitude, String latitude) {
        // 1. coordinate를 통한 reverse geocode 호출
        String coords = longitude + "," + latitude;
        MapResponse mapResponse = mapUtils.getReverseGeocode(coords); // POJO

        // 2. 응답 json mapResponseDto 변환 후 리턴
        return createMapResponseDto(mapResponse);
    }

    private MapDto.Response createMapResponseDto(MapResponse mapResponse) {
        String roadArr = mapResponse.getArea1() + " " + mapResponse.getArea2() + " " +mapResponse.getArea3() + " " +
                mapResponse.getRoad() + " " + mapResponse.getNumber() + " " + mapResponse.getDetail();

        return MapDto.Response.builder()
                .roadArr(roadArr)
                .zipcode(mapResponse.getZipcode())
                .build();
    }
}