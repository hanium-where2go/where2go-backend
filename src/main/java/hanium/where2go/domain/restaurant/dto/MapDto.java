package hanium.where2go.domain.restaurant.dto;

import lombok.Builder;

public class MapDto {

    @Builder
    public static class Response {
        private String longitude;
        private String latitude;
        private String roadArr; // 도로명 주소
        private String legalArr; // 법정동 주소
    }
}
