package dev.shubham.productservice.services;

import dev.shubham.productservice.exceptions.ProductNotFoundException;
import dev.shubham.productservice.models.Category;
import dev.shubham.productservice.models.Product;

import java.util.List;

public interface ProductService {

    public List<Product> getProduct();

    public Product getProductById(String id) throws ProductNotFoundException;

    public Product addProduct(String title,
                              String description,
                              double price,
                              String imageUrl,
                              String category);

    public Product updateProduct(String id, String title, String description, double price,String imageUrl,String category);

    public void deleteProduct(String id);

    public List<Product> getProductByCategory(String category);
    public List<String> getCategories();
}
