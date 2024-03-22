package istad.co.samplespringmvc.service.serviceImpl;

import istad.co.samplespringmvc.dto.CategoryRequest;
import istad.co.samplespringmvc.dto.CategoryResponse;
import istad.co.samplespringmvc.model.Category;
import istad.co.samplespringmvc.repository.CategoryRepository;
import istad.co.samplespringmvc.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    private CategoryResponse mapCategoryToResponse(Category category) {
        return CategoryResponse.builder()
                .id(category.getId())
                .title(category.getTitle())
                .description(category.getDescription())
                .build();
    }

    private Category mapRequestToCategory(CategoryRequest request) {
//        prevent null if the request fields are null
        return Category.builder()
                .title(request.title())
                .description(request.description())
                .build();
    }

    private Category searchCategoryByID(int id) {
        return categoryRepository.getAllCategories()
                .stream().filter(p -> p.getId() == id)
                .findFirst()
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Category doesn't exist!!"));
    }

    @Override
    public List<CategoryResponse> getAllCategories(String categoryName) {
        var Category = categoryRepository.getAllCategories();
        if (!categoryName.isEmpty()) {
            Category = Category.stream().filter(
                    pro -> pro.getTitle().toLowerCase().contains(categoryName.toLowerCase())
            ).toList();
        }
        return Category
                .stream()
                .map(pro -> {
                    return CategoryResponse.builder()
                            .id(pro.getId())
                            .title(pro.getTitle())
                            .description(pro.getDescription())
                            .build();
                }).toList();

    }


    @Override
    public CategoryResponse createCategory(CategoryRequest request) {
        Category newCategory = mapRequestToCategory(request);
        var maxID = categoryRepository.getAllCategories()
                .stream()
                .max(Comparator.comparingInt(Category::getId))
                .map(Category::getId);
        int newID = 1;
        if (maxID.isPresent()) {
            newID = maxID.get() + 1;
        }
        newCategory.setId(newID);
        categoryRepository.addCategory(newCategory);

        return mapCategoryToResponse(newCategory);

    }

    @Override
    public CategoryResponse findCategoryByID(int id) {
        return mapCategoryToResponse(searchCategoryByID(id));
    }


    @Override
    public void deleteCategory(int categoryId) {
        categoryRepository.getAllCategories().removeIf(p -> p.getId() == categoryId);
    }

    @Override
    public CategoryResponse updateCategory(int id, CategoryRequest categoryRequest) {
        // find if the Category exist
        var result = searchCategoryByID(id);
        result = mapRequestToCategory(categoryRequest);
        result.setId(id);
        categoryRepository.updateCategory(result);
        return mapCategoryToResponse(result);
    }
}
