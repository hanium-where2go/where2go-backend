package hanium.where2go.global.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import hanium.where2go.domain.restaurant.dto.MapDto;
import hanium.where2go.domain.restaurant.entity.Address;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class MapUtils { // Geocode, ReverseGeocode API를 호출하는 유틸리티

    @Value("${map.client-id}")
    private String clientId;
    @Value("${map.client-secret}")
    private String clientSecret;

    private static final String uri = "https://naveropenapi.apigw.ntruss.com";

    private WebClient webClient;
    private final MapResponseMapper mapResponseMapper;

    @PostConstruct
    public void init() {
        this.webClient = WebClient.builder()
                .baseUrl(uri)
                .defaultHeader("X-NCP-APIGW-API-KEY-ID", clientId)
                .defaultHeader("X-NCP-APIGW-API-KEY", clientSecret)
                .build();
    }

    public MapDto.UserLocationMapResponse getUserLocationMapResponse(String longtitude, String latitude) {
        // 1. 좌표 질의 API uri를 생성한다.
        String coords = longtitude + "," + latitude;
        String reverseUri = "/map-reversegeocode/v2/gc?coords=" + coords + "&sourcecrs=epsg:4326&orders=roadaddr&output=json";

        // 4. jsonNode 객체를 사용자 위치 응답 DTO로 전환한다.
        return mapResponseMapper.parseJsonToUserLocationResponseDto(sendAndReadJson(reverseUri), longtitude, latitude);
    }


    private JsonNode sendAndReadJson(String uri) {

        String response = webClient.get() // 2. map API 요청을 보내 json 응답을 받는다.
                .uri(uri)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        try { // 3. json string을 ObjectMapper를 통해 jsonNode 객체로 파싱한다.
            return objectMapper.readTree(response);
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}