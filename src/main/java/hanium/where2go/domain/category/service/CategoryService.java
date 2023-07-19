package hanium.where2go.domain.category.service;

import hanium.where2go.domain.category.dto.CategoryDto;
import hanium.where2go.domain.category.entity.Category;
import hanium.where2go.domain.category.repository.CategoryRepository;
import hanium.where2go.global.response.BaseException;
import hanium.where2go.global.response.ExceptionCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional
    public void createCategory(CategoryDto categoryDto) {
        Category category = Category.builder()
                .categoryName(categoryDto.getCategoryName())
                .build();

        categoryRepository.save(category);
    }

    @Transactional
    public void updateCategory(CategoryDto categoryDto, Long categoryId){
        Category findCategory = findCategoryById(categoryId);
        findCategory.changeCategoryName(categoryDto.getCategoryName());
    }

    public void deleteCategory(Long categoryId) {
        categoryRepository.delete(findCategoryById(categoryId));
    }

    public CategoryDto getCategoryById(Long categoryId) {
        Category findCategory = findCategoryById(categoryId);

        return CategoryDto.builder()
                .categoryId(findCategory.getId())
                .categoryName(findCategory.getCategoryName())
                .build();
    }

    public List<CategoryDto> getCategories() {
        return categoryRepository.findAll().stream().map(
                category -> CategoryDto.builder()
                        .categoryId(category.getId())
                        .categoryName(category.getCategoryName())
                        .build()
        ).collect(Collectors.toList());
    }

    private Category findCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new BaseException(ExceptionCode.CATEGORY_NOT_FOUND));
    }

}
