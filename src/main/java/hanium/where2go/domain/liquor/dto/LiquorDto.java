package hanium.where2go.domain.liquor.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LiquorDto {

    private Long liquorId;

    @NotBlank
    private String liquorName;
}
