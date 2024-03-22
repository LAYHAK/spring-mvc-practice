package istad.co.samplespringmvc.repository;


import istad.co.samplespringmvc.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

//DAO -> Data Access Object

@Repository
public class ProductRepository {
    private final List<Product> allProducts = new ArrayList<>() {{
        add(Product.builder()
                .id(1)
                .title("Coca")
                .description("this is the product one description")
                .price(3.4f)
                .imageUrl("productimage.jpg")
                .build());

        add(Product.builder()
                .id(2)
                .title("Sting")
                .description("this is the product two description")
                .price(3.4f)
                .imageUrl("productimagetwo.jpg")
                .build());

        add(Product.builder()
                .id(3)
                .title("Fanta")
                .description("this is the product one description")
                .price(3.4f)
                .imageUrl("productimagethree.jpg")
                .build());
    }};

    public List<Product> getAllProducts() {
        return allProducts;
    }

    public void addProduct(Product product) {
        allProducts.add(product);
    }

    public void updateProduct(Product product) {
        // need find the index of the product
        int index = allProducts.indexOf(
                allProducts.stream()
                        .filter(pro -> pro.getId() == product.getId())
                        .findFirst()
                        .orElse(null)
        );
        allProducts.set(index, product);
    }

    public void deleteProduct(Product product) {
        allProducts.remove(product);
    }

//    public static void main(String[] args) {
//        var repo = new ProductRepository();
//        var searchProduct = Product.builder()
//                .id(4)
//                .title("product title three ")
//                .description("this is the product one description")
//                .price(3.4f)
//                .imageUrl("productimagethree.jpg")
//                .build();
//        System.out.println(repo.allProducts.indexOf(searchProduct));
//
//    }
}
