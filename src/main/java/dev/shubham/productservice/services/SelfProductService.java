package dev.shubham.productservice.services;

import dev.shubham.productservice.dtos.CreateFakeStoreRequestDto;
import dev.shubham.productservice.dtos.FakeStoreResponseDto;
import dev.shubham.productservice.exceptions.ProductNotFoundException;
import dev.shubham.productservice.models.Category;
import dev.shubham.productservice.models.Product;
import dev.shubham.productservice.repositories.CategoryRepository;
import dev.shubham.productservice.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("selfProductService")
public class SelfProductService implements ProductService {

    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;
    public SelfProductService(ProductRepository productRepository,CategoryRepository categoryRepository){
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }
    @Override
    public List<Product> getProduct() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(Long id) throws ProductNotFoundException {
        return productRepository.findByIdIs(id);
    }

    @Override
    public Product addProduct(String title, String description, double price, String imageUrl, String category) {
        Product product = new Product();
        product.setTitle(title);
        product.setDescription(description);
        product.setPrice(price);
        product.setImageUrl(imageUrl);

        Category productCategory = categoryRepository.findByTitle(category);
        if(productCategory == null){
            Category newCategory = new Category();
            newCategory.setTitle(category);
            productCategory = newCategory;
        }
        product.setCategory(productCategory);
        Product savedProduct = productRepository.save(product);
        return savedProduct;
    }

    @Override
    public Product updateProduct(Long id, String title, String description, double price, String imageUrl, String category) {
        Product update = new Product();
        update.setId(id);
        update.setTitle(title);
        update.setDescription(description);
        update.setPrice(price);
        update.setImageUrl(imageUrl);
        Category category1 = new Category();
        category1.setTitle(category);
        update.setCategory(category1);
        return productRepository.save(update);
    }

    @Override
    public void deleteProduct(Long id) {
           productRepository.deleteById(id);
    }

    @Override
    public List<Product> getProductByCategory(String category) {
        return productRepository.findByCategoryTitle(category);
    }

    @Override
    public List<String> getCategories() {
        List<Category> category =  categoryRepository.findAll();
        List<String> categories  = new ArrayList<>();
        for (Category category1 : category) {
            categories.add(category1.getTitle());
        }
        return categories;
    }
}
