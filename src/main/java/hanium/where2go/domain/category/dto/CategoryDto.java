package hanium.where2go.domain.category.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CategoryDto {
    private Long categoryId;
    private String categoryName;
}
