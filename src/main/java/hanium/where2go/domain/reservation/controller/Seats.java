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
    @JsonProperty("seats") // JSON의 필드명과 일치시킵니다.
    private Integer seat;
}