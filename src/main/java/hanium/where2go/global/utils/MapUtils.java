package hanium.where2go.global.utils;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class MapUtils { // Geocode, ReverseGeocode API를 호출하는 유틸리티

    private static String clientId;
    private static String clientSecret;

    @Value("${map.client-id}")
    private void setClientId(String client_id) {
        MapUtils.clientId = client_id;
    }

    @Value("${map.client-secret}")
    private void setClientSecret(String client_secret) {
        MapUtils.clientSecret = client_secret;
    }

    private static String uri = "https://naveropenapi.apigw.ntruss.com";

    private static WebClient webClient;

    @PostConstruct
    public void init() {
        MapUtils.webClient = WebClient.builder()
                .baseUrl(uri)
                .defaultHeader("X-NCP-APIGW-API-KEY-ID", clientId)
                .defaultHeader("X-NCP-APIGW-API-KEY", clientSecret)
                .build();
    }

    /**
     * @param coords 주소로 변환할 좌표. 경도,위도 좌표계를 공백 없이 10,20 형태의 String 타입으로 받습니다.
     * @return 위도, 경도에 해당하는 주소를 String으로 리턴합니다.
     */
    public static String getReverseGeocode(String coords) {

        String reverseUri = "/map-reversegeocode/v2/gc?coords=" + coords + "&sourcecrs=epsg:4326&orders=roadaddr&output=json";

        String response = webClient.get()
                .uri(reverseUri)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        return response; // TODO json -> java객체로 파싱
    }

    /**
     * @param address 검색할 주소.
     * @param coords  검색 중심 좌표. 경도,위도 좌표계를 공백 없이 10,20 형태의 String 타입으로 받습니다.
     * @return 검색 결과로 주소 목록과 세부 정보를 JSON 형태로 반환합니다.
     */
    public static String getGeocode(String address, String coords) {

        String geocodeUri = "/map-geocode/v2/geocode?query=" + address + "&coordinate=" + coords;

        String response = webClient.get()
                .uri(geocodeUri)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        return response; // TODO json -> java 객체로 파싱
    }
}