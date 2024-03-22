package istad.co.samplespringmvc.repository;


import istad.co.samplespringmvc.model.Category;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

//DAO -> Data Access Object

@Repository
public class CategoryRepository {
    private final List<Category> allCategories = new ArrayList<>() {{
        add(Category.builder()
                .id(1)
                .title("Clothes")
                .description("this is the Category Clothes description")
                .build());

        add(Category.builder()
                .id(2)
                .title("Drinks")
                .description("this is the Category Drinks description")
                .build());

        add(Category.builder()
                .id(3)
                .title("Food")
                .description("this is the Category Food description")
                .build());
    }};

    public List<Category> getAllCategories() {
        return allCategories;
    }

    public void addCategory(Category Category) {
        allCategories.add(Category);
    }

    public void updateCategory(Category Category) {
        // need find the index of the Category
        int index = allCategories.indexOf(
                allCategories.stream()
                        .filter(pro -> pro.getId() == Category.getId())
                        .findFirst()
                        .orElse(null)
        );
        allCategories.set(index, Category);
    }

    public void deleteCategory(Category Category) {
        allCategories.remove(Category);
    }

//    public static void main(String[] args) {
//        var repo = new CategoryRepository();
//        var searchCategory = Category.builder()
//                .id(4)
//                .title("Category title three ")
//                .description("this is the Category one description")
//                .price(3.4f)
//                .imageUrl("Categoryimagethree.jpg")
//                .build();
//        System.out.println(repo.allCategories.indexOf(searchCategory));
//
//    }
}
