package hanium.where2go.domain.category.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CategoryDto {

    private Long categoryId;

    @NotBlank
    private String categoryName;
}
