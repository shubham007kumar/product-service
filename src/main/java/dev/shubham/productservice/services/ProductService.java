package dev.shubham.productservice.services;

import dev.shubham.productservice.exceptions.ProductNotFoundException;
import dev.shubham.productservice.models.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    public List<Product> getProduct();

    public Product getProductById(Long id) throws ProductNotFoundException;

    public Product addProduct(String title,
                              String description,
                              double price,
                              String imageUrl,
                              String category);

    public Product updateProduct(Long id, String title, String description, double price,String imageUrl,String category);

    public void deleteProduct(Long id);

    public List<Product> getProductByCategory(String category);
    public List<String> getCategories();
}
