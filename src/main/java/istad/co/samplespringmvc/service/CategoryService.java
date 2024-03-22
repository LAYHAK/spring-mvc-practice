package istad.co.samplespringmvc.service;

import istad.co.samplespringmvc.dto.CategoryRequest;
import istad.co.samplespringmvc.dto.CategoryResponse;

import java.util.List;

public interface CategoryService {
    List<CategoryResponse> getAllCategories(String categoryName);
    CategoryResponse createCategory(CategoryRequest categoryRequest);
    CategoryResponse findCategoryByID(int id );
    void deleteCategory(int categoryId);

    CategoryResponse updateCategory(int id ,  CategoryRequest categoryRequest);
}
