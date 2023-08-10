package hanium.where2go.global.utils;

import com.fasterxml.jackson.databind.JsonNode;
import hanium.where2go.domain.restaurant.dto.MapDto;
import org.springframework.stereotype.Component;

@Component
public class MapResponseMapper {

    // Reverse Geocode 응답 json을 사용자 위치 기반 도로명주소 DTO로 변환하는 메서드
    public MapDto.UserLocationMapResponse parseJsonToUserLocationResponseDto(JsonNode jsonNode, String longitude, String latitude) {
        JsonNode result = jsonNode.get("results").get(0);

        return MapDto.UserLocationMapResponse.builder()
                .longitude(longitude)
                .latitude(latitude)
                .roadAddr(getRoadAddr(result.get("region"), result.get("land")))
                .build();
    }

    private String getRoadAddr(JsonNode area, JsonNode land) {
        return area.get("area1").get("name").asText() + " " + area.get("area2").get("name").asText() + " " +
                area.get("area3").get("name").asText() + " " + land.get("name").asText() + " " +
                land.get("number1").asText() + " " + land.get("addition0").get("value").asText();
    }


}
