package hanium.where2go.domain.restaurant.service;

import hanium.where2go.domain.restaurant.dto.MapDto;
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
        String addrJson = mapUtils.getGeocode(keywords);

        // 2. 응답 json mapResponseDto 변환 후 리턴
        System.out.println(addrJson);
        return null;
    }

    @Override
    public MapDto.Response getAddrByCoords(String longitude, String latitude) {
        // 1. coordinate를 통한 reverse geocode 호출
        String coords = longitude + "," + latitude;
        String addrJson = mapUtils.getReverseGeocode(coords);

        // 2. 응답 json mapResponseDto 변환 후 리턴
        System.out.println(addrJson);
        return null;
    }
}
