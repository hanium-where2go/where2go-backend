package hanium.where2go.domain.liquor.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LiquorDto {
    private Long liquorId;
    private String liquorName;
}
