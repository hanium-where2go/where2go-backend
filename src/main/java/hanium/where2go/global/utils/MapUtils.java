package hanium.where2go.global.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import hanium.where2go.domain.restaurant.entity.Address;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class MapUtils { // Geocode, ReverseGeocode API를 호출하는 유틸리티

    @Value("${map.client-id}")
    private String clientId;
    @Value("${map.client-secret}")
    private String clientSecret;

    private static final String uri = "https://naveropenapi.apigw.ntruss.com";

    private WebClient webClient;

    @PostConstruct
    public void init() {
        this.webClient = WebClient.builder()
                .baseUrl(uri)
                .defaultHeader("X-NCP-APIGW-API-KEY-ID", clientId)
                .defaultHeader("X-NCP-APIGW-API-KEY", clientSecret)
                .build();
    }

    /**
     * @param coords 주소로 변환할 좌표. 경도,위도 좌표계를 공백 없이 10,20 형태의 String 타입으로 받습니다.
     * @return 위도, 경도에 해당하는 주소를 String으로 리턴합니다.
     */
    public MapResponse getReverseGeocode(String coords) {

        String reverseUri = "/map-reversegeocode/v2/gc?coords=" + coords + "&sourcecrs=epsg:4326&orders=roadaddr&output=json";

        String response = webClient.get()
                .uri(reverseUri)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        return getRoadAddress(response);
    }

    /**
     * @param address 검색할 주소.
     * @param coords  검색 중심 좌표. 경도,위도 좌표계를 공백 없이 10,20 형태의 String 타입으로 받습니다.
     * @return 검색 결과로 주소 목록과 세부 정보를 JSON 형태로 반환합니다.
     */
    public MapResponse getGeocode(String address) {

        String geocodeUri = "/map-geocode/v2/geocode?query=" + address;

        String response = webClient.get()
                .uri(geocodeUri)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        return getRoadAddress(response);
    }

    private MapResponse getRoadAddress(String jsonAddr) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        try { // json string을 MapResponse 객체로 파싱
            JsonNode jsonNode = objectMapper.readTree(jsonAddr);
            return new MapResponse(jsonNode);
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}