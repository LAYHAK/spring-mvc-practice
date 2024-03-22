package istad.co.samplespringmvc.restcontroller;

import istad.co.samplespringmvc.dto.CategoryRequest;
import istad.co.samplespringmvc.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
public class CategoryRestController {
    private final CategoryService categoryService;
    private Map<String, Object> response(Object object, String message, int status) {
        HashMap<String, Object> response = new HashMap<>();
        response.put("payload", object);
        response.put("message", message);
        response.put("status", status);
        return response;
    }

    // ?CategoryName=fanta
    @GetMapping("/get-all")
    public Map<String, Object> getAllCategories(
            @RequestParam(defaultValue = "") String categoryName) {
        return response(
                categoryService.getAllCategories(categoryName),
                "Successfully Retrieved all data!",
                HttpStatus.OK.value()
        );

    }

    @PostMapping("/new-category")
    public Map<String, Object> createNewCategory(@RequestBody CategoryRequest request) {
        return response(
                categoryService.createCategory(request),
                "Created New Category Successfully!",
                HttpStatus.CREATED.value());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Map<String, Object> findCategoryByID(@PathVariable int id) {
        return response(
                categoryService.findCategoryByID(id),
                "Successfully Retrieved the record!",
                HttpStatus.FOUND.value());
    }

    @PatchMapping("/{id}")
    public Map<String, Object> updateCategory(@PathVariable int id, @RequestBody CategoryRequest request) {
        return response(
                categoryService.updateCategory(id, request),
                "Update Category Successfully",
                HttpStatus.OK.value()
        );
    }

    @DeleteMapping("/{id}")
    public Map<String, Object> deleteCategory(@PathVariable int id) {
        categoryService.deleteCategory(id);
        return response(
                null,
                "Category Deleted Successfully",
                HttpStatus.OK.value()
        );
    }
}
