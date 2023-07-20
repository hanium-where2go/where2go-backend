package hanium.where2go.domain.liquor.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LiquorDto {

    private Long liquorId;

    @NotBlank
    private String liquorName;
}
