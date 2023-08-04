package hanium.where2go.domain.restaurant.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class MapDto {

    @Builder
    @Getter
    @Setter
    public static class Response {
        private String roadArr; // 도로명 주소
        private String zipcode;
    }
}
