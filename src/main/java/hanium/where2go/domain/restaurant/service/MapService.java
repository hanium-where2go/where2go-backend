package hanium.where2go.domain.restaurant.service;

import hanium.where2go.domain.restaurant.dto.MapDto;

public interface MapService {
    MapDto.Response getAddrByKeywords(String keywords);
    MapDto.Response getAddrByCoords(String longitude, String latitude);
}
