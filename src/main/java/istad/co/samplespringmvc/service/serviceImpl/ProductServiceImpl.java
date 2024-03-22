package istad.co.samplespringmvc.service.serviceImpl;

import istad.co.samplespringmvc.dto.ProductRequest;
import istad.co.samplespringmvc.dto.ProductResponse;
import istad.co.samplespringmvc.model.Product;
import istad.co.samplespringmvc.repository.ProductRepository;
import istad.co.samplespringmvc.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    private ProductResponse mapProductToResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .imageUrl(product.getImageUrl())
                .title(product.getTitle())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }

    private Product mapRequestToProduct(ProductRequest request) {
//        prevent null if the request fields are null
        return Product.builder()
                .title(request.title())
                .price(request.price())
                .imageUrl(request.imageUrl())
                .description(request.description())
                .build();
    }

    private Product searchProductByID(int id) {
        return productRepository.getAllProducts()
                .stream().filter(p -> p.getId() == id)
                .findFirst()
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Product doesn't exist!!"));
    }

    @Override
    public List<ProductResponse> getAllProduct(String productName) {
        var product = productRepository.getAllProducts();
        if (!productName.isEmpty()) {
            product = product.stream().filter(
                    pro -> pro.getTitle().toLowerCase().contains(productName.toLowerCase())
            ).toList();
        }
        return product
                .stream()
                .map(pro -> {
                    return ProductResponse.builder()
                            .id(pro.getId())
                            .imageUrl(pro.getImageUrl())
                            .price(pro.getPrice())
                            .title(pro.getTitle())
                            .description(pro.getDescription())
                            .build();
                }).toList();

    }

    @Override
    public ProductResponse createProduct(ProductRequest request) {
        Product newProduct = mapRequestToProduct(request);
        var maxID = productRepository.getAllProducts()
                .stream()
                .max(Comparator.comparingInt(Product::getId))
                .map(Product::getId);
        int newID = 1;
        if (maxID.isPresent()) {
            newID = maxID.get() + 1;
        }
        newProduct.setId(newID);
        productRepository.addProduct(newProduct);

        return mapProductToResponse(newProduct);

    }

    @Override
    public ProductResponse findProductByID(int id) {
        return mapProductToResponse(searchProductByID(id));
    }


    @Override
    public void deleteProduct(int productId) {
        productRepository.getAllProducts().removeIf(p -> p.getId() == productId);
    }

    @Override
    public ProductResponse updateProduct(int id, ProductRequest productRequest) {
        // find if the product exist
        var result = searchProductByID(id);
        result = mapRequestToProduct(productRequest);
        result.setId(id);
        productRepository.updateProduct(result);
        return mapProductToResponse(result);
    }
}
