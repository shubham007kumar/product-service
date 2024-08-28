package dev.shubham.productservice.services;

import dev.shubham.productservice.dtos.FakeStoreResponseDto;
import dev.shubham.productservice.exceptions.ProductNotFoundException;
import dev.shubham.productservice.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("fakeProductService")
public class FakeProductService implements ProductService {

    private final RestTemplate restTemplate;

    public FakeProductService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<Product> getProduct() {
        ResponseEntity response = restTemplate.getForEntity("https://fakestoreapi.com/products", FakeStoreResponseDto[].class);
        FakeStoreResponseDto[] response1 = (FakeStoreResponseDto[]) response.getBody();
        ArrayList<Product> products = new ArrayList<>();
        for (FakeStoreResponseDto fakeStoreResponseDto : response1) {
            products.add(fakeStoreResponseDto.toProduct());
        }
        return products;
    }

    @Override
    public Product addProduct(String title,
                              String description,
                              double price,
                              String imageUrl,
                              String category) {
        FakeStoreResponseDto fakeStoreResponseDto = new FakeStoreResponseDto();
        fakeStoreResponseDto.setTitle(title);
        fakeStoreResponseDto.setDescription(description);
        fakeStoreResponseDto.setPrice(price);
        fakeStoreResponseDto.setCategory(category);
        fakeStoreResponseDto.setImage(imageUrl);

        ResponseEntity response = restTemplate.postForEntity("https://fakestoreapi.com/products", fakeStoreResponseDto, FakeStoreResponseDto.class);
        FakeStoreResponseDto fakeStoreResponseDto1 = (FakeStoreResponseDto) response.getBody();
        return fakeStoreResponseDto1.toProduct();
    }

    @Override
    public Product getProductById(Long id) throws ProductNotFoundException {
        ResponseEntity response = restTemplate.getForEntity("https://fakestoreapi.com/products/" + id, FakeStoreResponseDto.class);
        FakeStoreResponseDto fakeStoreResponseDto = (FakeStoreResponseDto) response.getBody();
        if (fakeStoreResponseDto == null) {
            throw new ProductNotFoundException("Product with id: " + id + " doesn't exist. Retry some other product.");
        }
        return fakeStoreResponseDto.toProduct();
    }

    @Override
    public Product updateProduct(Long id,String title,String description, double price,String imageUrl,String category) {
        FakeStoreResponseDto update = new FakeStoreResponseDto();
        update.setTitle(title);
        update.setDescription(description);
        update.setPrice(price);
        update.setCategory(category);
        update.setImage(imageUrl);
        restTemplate.put("https://fakestoreapi.com/products/" + id, update);

        return update.toProduct();

    }
    @Override
    public void deleteProduct(Long id) {
        restTemplate.delete("https://fakestoreapi.com/products/" + id);
    }

    @Override
    public List<Product> getProductByCategory(String category){
        ResponseEntity response = restTemplate.getForEntity("https://fakestoreapi.com/products/category/" + category, FakeStoreResponseDto[].class);
        FakeStoreResponseDto[] responseData = (FakeStoreResponseDto[]) response.getBody();
        ArrayList<Product> products = new ArrayList<>();
        for (FakeStoreResponseDto fakeStoreResponseDto : responseData) {
            products.add(fakeStoreResponseDto.toProduct());
        }
        return products;
    }

    @Override
    public List<String> getCategories(){
        ResponseEntity response = restTemplate.getForEntity("https://fakestoreapi.com/products/categories", String[].class);
        String[] response1 = (String[]) response.getBody();
        ArrayList<String> categories = new ArrayList<>();
        for (String category : response1) {
            categories.add(category);
        }
        return categories;
    }

    @Override
    public boolean generateProductData() {
        return false;
    }

    @Override
    public Page<Product> getProducts(Integer pageSize,Integer pageNumber,String sort) {
        return null;
    }

}
