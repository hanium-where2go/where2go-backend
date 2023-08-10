package hanium.where2go.domain.restaurant.service;

import hanium.where2go.domain.restaurant.dto.MapDto;

public interface MapService {
    MapDto.KeywordMapResponse getAddrByKeywords(String keywords);
    MapDto.UserLocationMapResponse getAddrByCoords(String longitude, String latitude);
}
