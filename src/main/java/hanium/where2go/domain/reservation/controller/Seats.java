package hanium.where2go.domain.reservation.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Seats {
    private Integer seats;
    private Long restaurantId; // restaurantId의 타입을 Long으로 변경

    // @JsonProperty를 사용하여 JSON 필드와 객체 필드의 이름을 매핑
    @JsonProperty("restaurantId")
    public Long getRestaurantId() {
        return restaurantId;
    }
}