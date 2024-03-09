package dev.shubham.productservice.controllers;

import dev.shubham.productservice.dtos.CreateFakeStoreRequestDto;
import dev.shubham.productservice.exceptions.ProductNotFoundException;
import dev.shubham.productservice.models.Product;
import dev.shubham.productservice.services.ProductService;
import dev.shubham.productservice.services.SelfProductService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.sound.sampled.Port;
import java.util.List;

@RestController
public class ProductController {

    private ProductService productService;
    public ProductController(@Qualifier("selfProductService") ProductService productService){
        this.productService = productService;
    }

    @GetMapping("/product")
    public ResponseEntity<List<Product>> getProduct() {
        List<Product> products = productService.getProduct();
        ResponseEntity<List<Product>> response = new ResponseEntity<>(products, HttpStatus.valueOf(200));
        return response;
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") Long id) throws ProductNotFoundException {
        Product product = productService.getProductById(id);
        ResponseEntity<Product> response = new ResponseEntity<>(product,HttpStatus.valueOf(202));
        return response;
    }
//
    @PostMapping("/product")
    public Product addProduct(@RequestBody CreateFakeStoreRequestDto createFakeStoreRequestDto) {
        return productService.addProduct(createFakeStoreRequestDto.getTitle(),
                createFakeStoreRequestDto.getDescription(),
                createFakeStoreRequestDto.getPrice(),
                createFakeStoreRequestDto.getImageUrl(),
                createFakeStoreRequestDto.getCategory());
    }

    @PutMapping("/product/{id}")
    public ResponseEntity<Product> updateProduct(@RequestBody CreateFakeStoreRequestDto createFakeStoreRequestDto, @PathVariable("id") Long id) {
        Product product =  productService.updateProduct(id,
                createFakeStoreRequestDto.getTitle(),
                createFakeStoreRequestDto.getDescription(),
                createFakeStoreRequestDto.getPrice(),
                createFakeStoreRequestDto.getImageUrl(),
                createFakeStoreRequestDto.getCategory());
        ResponseEntity<Product> response = new ResponseEntity<>(product,HttpStatus.valueOf(200));
        return  response;
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity deleteProduct(@PathVariable("id") Long id) {
        productService.deleteProduct(id);
        ResponseEntity response = new ResponseEntity(HttpStatus.valueOf(202));
        return response;
    }

    @GetMapping("/product/category/{category}")
    public ResponseEntity<List<Product>> getProductByCategory(@PathVariable("category") String category){
        List<Product> products = productService.getProductByCategory(category);
        ResponseEntity<List<Product>> response = new ResponseEntity<>(products,HttpStatus.valueOf(200));
        return response;
    }
//
    @GetMapping("/product/category")
    public ResponseEntity<List<String>> getCategories(){
        List<String> category =  productService.getCategories();
        ResponseEntity<List<String>> response = new ResponseEntity<>(category,HttpStatus.valueOf(200));
        return response;
    }
}
