package dev.shubham.productservice.controllers;

import dev.shubham.productservice.dtos.CreateFakeStoreRequestDto;
import dev.shubham.productservice.models.Product;
import dev.shubham.productservice.services.ProductService;
import jakarta.websocket.server.PathParam;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/product")
    public List<Product> getProduct() {
        return productService.getProduct();
    }

    @GetMapping("/product/{id}")
    public Product getProductById(@PathVariable("id") String id) {
        return productService.getProductById(id);
    }

    @PostMapping("/product")
    public Product addProduct(@RequestBody CreateFakeStoreRequestDto createFakeStoreRequestDto) {
        return productService.addProduct(createFakeStoreRequestDto.getTitle(),
                createFakeStoreRequestDto.getDescription(),
                createFakeStoreRequestDto.getPrice(),
                createFakeStoreRequestDto.getImageUrl(),
                createFakeStoreRequestDto.getCategory());
    }

    @PutMapping("/product/{id}")
    public Product updateProduct(@RequestBody CreateFakeStoreRequestDto createFakeStoreRequestDto, @PathVariable("id") String id) {
        return productService.updateProduct(id,
                createFakeStoreRequestDto.getTitle(),
                createFakeStoreRequestDto.getDescription(),
                createFakeStoreRequestDto.getPrice(),
                createFakeStoreRequestDto.getImageUrl(),
                createFakeStoreRequestDto.getCategory());
    }

    @DeleteMapping("/product/{id}")
    public void deleteProduct(@PathVariable("id") String id) {
        productService.deleteProduct(id);
    }

    @GetMapping("/product/category/{category}")
    public List<Product> getProductByCategory(@PathVariable String category){
        return productService.getProductByCategory(category);
    }

    @GetMapping("/product/category")
    public List<String> getCategories(){
        return productService.getCategories();
    }
}
