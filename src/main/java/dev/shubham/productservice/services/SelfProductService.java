package dev.shubham.productservice.services;

import dev.shubham.productservice.exceptions.ProductNotFoundException;
import dev.shubham.productservice.models.Category;
import dev.shubham.productservice.models.Product;
import dev.shubham.productservice.repositories.CategoryRepository;
import dev.shubham.productservice.repositories.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

    private static final String[] PRODUCT_NAMES = {
            "TV", "Headphones", "Smartphone", "Laptop", "Tablet",
            "Camera", "Speaker", "Drone", "Gaming Console", "Smartwatch",
            "Fitness Tracker", "External Hard Drive", "Monitor", "Router",
            "Printer", "Keyboard", "Mouse", "Earbuds", "Projector", "Desk"
    };
    private static final String[] CATEGORY_NAMES = {
            "Electronics","HOME","SPORT"
    };
    @Override
    public boolean generateProductData() {
        List<Product> products = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < 20; i++) {
            String productName = PRODUCT_NAMES[random.nextInt(PRODUCT_NAMES.length)];
            int productPrice = random.nextInt(10000) + 100; // Random price between 100 and 10000
            Product product = new Product();
            Category category = new Category();
            product.setTitle(productName + " " + (i + 1));
            product.setDescription("Description for " + productName + " " + (i + 1));
            product.setPrice(productPrice);
            product.setImageUrl("path/to/image_" + productName.toLowerCase() + "_" + (i + 1) + ".jpg");
            String categoryName = CATEGORY_NAMES[random.nextInt(CATEGORY_NAMES.length)];
            category.setTitle(categoryName);
            product.setCategory(category);
            products.add(product);
        }
        productRepository.saveAll(products);
        return true;
    }

    @Override
    public Page<Product> getProducts(Integer pageSize,Integer pageNumber,String sort) {
        Pageable pageable = null;
        if(sort != null){
          pageable = PageRequest.of(pageNumber,pageSize, Sort.Direction.DESC,sort);
        }else{
           pageable = PageRequest.of(pageNumber,pageSize);
        }
        return productRepository.findAll(pageable);
    }
}
