package hanium.where2go.domain.restaurant.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class MapDto {

    @Builder
    @Getter
    @Setter
    public static class UserLocationMapResponse {
        private String longitude;
        private String latitude;
        private String roadAddr; // 도로명 주소
    }

    @Builder
    @Getter
    @Setter
    public static class KeywordMapResponse {
        private String roadAddr;
        private String jibunAddr;
        private String longitude;
        private String latitude;
    }
}
