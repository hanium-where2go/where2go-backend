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
    public MapDto.KeywordMapResponses getAddrByKeywords(String keyword) {
        // keyword를 통한 geocode 호출 및 도로명주소 반환
        return mapUtils.getKeywordMapResponse(keyword);
    }

    @Override
    public MapDto.UserLocationMapResponse getAddrByCoords(String longitude, String latitude) {
        // coordinate를 통한 reverse geocode 호출 및 도로명주소 반환
        return mapUtils.getUserLocationMapResponse(longitude, latitude);
    }
}