package hanium.where2go.global.utils;

import com.fasterxml.jackson.databind.JsonNode;
import hanium.where2go.domain.restaurant.dto.MapDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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

    // geocode response 리스트 + 페이지 데이터를 DTO로 매핑하는 메서드
    public MapDto.KeywordMapResponses parseJsonToKeywordMapResponsesDto(JsonNode result) {

        JsonNode meta = result.get("meta");

        List<MapDto.KeywordMapResponse> keywordMapResponses =
                StreamSupport.stream(result.get("addresses").spliterator(),false).map(
                        address -> parseJsonToKeywordMapResponseDto(address)
                ).collect(Collectors.toList());

        return MapDto.KeywordMapResponses.builder()
                .keywordMapResponses(keywordMapResponses)
                .totalCount(meta.get("totalCount").asInt())
                .page(meta.get("page").asInt())
                .count(meta.get("count").asInt())
                .build();
    }

    // geocode response 각각을 DTO로 매핑하는 메서드
    private MapDto.KeywordMapResponse parseJsonToKeywordMapResponseDto(JsonNode address) {
        return MapDto.KeywordMapResponse.builder()
                .roadAddr(address.get("roadAddress").asText())
                .jibunAddr(address.get("jibunAddress").asText())
                .longitude(address.get("y").asText())
                .latitude(address.get("x").asText())
                .build();
    }

}
