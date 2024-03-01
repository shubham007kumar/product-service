package dev.shubham.productservice.services;

import dev.shubham.productservice.dtos.FakeStoreResponseDto;
import dev.shubham.productservice.models.Product;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class FakeProductService implements ProductService {

    private final RestTemplate restTemplate;

    public FakeProductService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<Product> getProduct() {
        FakeStoreResponseDto[] response = restTemplate.getForObject("https://fakestoreapi.com/products", FakeStoreResponseDto[].class);
        ArrayList<Product> products = new ArrayList<>();
        assert response != null;
        for (FakeStoreResponseDto fakeStoreResponseDto : response) {
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

        FakeStoreResponseDto response = restTemplate.postForObject("https://fakestoreapi.com/products", fakeStoreResponseDto, FakeStoreResponseDto.class);
        assert response != null;
        return response.toProduct();

    }

    @Override
    public Product getProductById(String id) {
        FakeStoreResponseDto fakeStoreResponseDto = restTemplate.getForObject("https://fakestoreapi.com/products/" + id, FakeStoreResponseDto.class);
        assert fakeStoreResponseDto != null;
        return fakeStoreResponseDto.toProduct();
    }

    @Override
    public Product updateProduct(String id,String title,String description, double price,String imageUrl,String category) {
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
    public void deleteProduct(String id) {
        restTemplate.delete("https://fakestoreapi.com/products/" + id);
    }

    @Override
    public List<Product> getProductByCategory(String category){
        FakeStoreResponseDto[] response = restTemplate.getForObject("https://fakestoreapi.com/products/category/" + category, FakeStoreResponseDto[].class);
        ArrayList<Product> products = new ArrayList<>();
        assert response != null;
        for (FakeStoreResponseDto fakeStoreResponseDto : response) {
            products.add(fakeStoreResponseDto.toProduct());
        }
        return products;
    }

    @Override
    public List<String> getCategories(){
        String[] response = restTemplate.getForObject("https://fakestoreapi.com/products/categories", String[].class);
        ArrayList<String> categories = new ArrayList<>();
        assert response != null;
        for (String category : response) {
            categories.add(category);
        }
        return categories;
    }

}
