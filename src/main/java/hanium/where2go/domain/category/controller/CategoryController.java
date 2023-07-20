package hanium.where2go.domain.category.controller;

import hanium.where2go.domain.category.dto.CategoryDto;
import hanium.where2go.domain.category.service.CategoryService;
import hanium.where2go.global.response.BaseResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<BaseResponse> postCategory(@Valid @RequestBody CategoryDto categoryDto) {
        categoryService.createCategory(categoryDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new BaseResponse(HttpStatus.CREATED.value(), "카테고리가 생성되었습니다.", null));
    }

    @PatchMapping("/{category-id}")
    public ResponseEntity<BaseResponse> patchCategory(@Valid @RequestBody CategoryDto categoryDto,
                                                      @PathVariable("category-id") @Min(1) Long categoryId) {
        categoryService.updateCategory(categoryDto, categoryId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new BaseResponse(HttpStatus.OK.value(), "카테고리가 수정되었습니다.", null));
    }

    @GetMapping
    public ResponseEntity<BaseResponse<List<CategoryDto>>> getCategories() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new BaseResponse(HttpStatus.OK.value(), "카테고리 목록을 불러왔습니다.", categoryService.getCategories()));
    }

    @GetMapping("/{category-id}")
    public ResponseEntity<BaseResponse<CategoryDto>> getCategory(@PathVariable("category-id") @Min(1) Long categoryId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new BaseResponse(HttpStatus.OK.value(), "카테고리를 불러왔습니다.", categoryService.getCategoryById(categoryId)));
    }

    @DeleteMapping("/{category-id}")
    public ResponseEntity<BaseResponse> deleteCategory(@PathVariable("category-id") @Min(1) Long categoryId) {
        categoryService.deleteCategory(categoryId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(new BaseResponse(HttpStatus.NO_CONTENT.value(), "카테고리를 삭제했습니다.", null));
    }
}
