package dev.shubham.productservice.controllers;

import dev.shubham.productservice.dtos.CreateFakeStoreRequestDto;
import dev.shubham.productservice.dtos.UserDto;
import dev.shubham.productservice.exceptions.ProductNotFoundException;
import dev.shubham.productservice.models.Product;
import dev.shubham.productservice.services.ProductService;
import dev.shubham.productservice.services.SelfProductService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.sound.sampled.Port;
import java.util.List;

@RestController
public class ProductController {

    private ProductService productService;
    private RestTemplate restTemplate;
    private RedisTemplate<String,Object> redisTemplate;
    public ProductController(@Qualifier("selfProductService") ProductService productService,
                             RestTemplate restTemplate,
                             RedisTemplate<String,Object> redisTemplate){
        this.productService = productService;
        this.restTemplate = restTemplate;
        this.redisTemplate = redisTemplate;
    }

    @GetMapping("/product")
    public ResponseEntity<List<Product>> getProduct() {
        List<Product> products = productService.getProduct();
        ResponseEntity<List<Product>> response = new ResponseEntity<>(products, HttpStatus.valueOf(200));
        return response;
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") Long id) throws ProductNotFoundException {
        Product product = (Product) redisTemplate.opsForHash().get("PRODUCT","PRODUCT"+id);
        if(product != null){
            return new ResponseEntity<>(product,HttpStatus.valueOf(200));
        }
         product = productService.getProductById(id);
        ResponseEntity<Product> response = new ResponseEntity<>(product,HttpStatus.valueOf(202));
        UserDto userDto =
                restTemplate.getForObject("http://userservice/user/1", UserDto.class);
        redisTemplate.opsForHash().put("PRODUCT","PRODUCT"+id,product);
        return response;
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

    @CacheEvict(value = "PRODUCT",key = "#PRODUCT.id")
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

    @GetMapping("/product-bulk")
    public ResponseEntity generateData(){
        return ResponseEntity.ok(productService.generateProductData());
    }

    @GetMapping("/product/{pageSize}/{pageNumber}")
    public ResponseEntity<List<Product>> getProductByPage(@PathVariable("pageSize") Integer pageSize,@PathVariable("pageNumber") Integer pageNumber,@PathVariable(required = false) String sort) {
        Page<Product> product;
        if(sort != null){
            product = productService.getProducts(pageSize,pageNumber,sort);
        }else{
           product = productService.getProducts(pageSize,pageNumber,null);
        }
        ResponseEntity<List<Product>> response = new ResponseEntity<>(product.getContent(),HttpStatus.valueOf(200));
        return response;
    }
}
