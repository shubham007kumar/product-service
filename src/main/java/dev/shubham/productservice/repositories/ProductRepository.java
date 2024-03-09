package dev.shubham.productservice.repositories;

import dev.shubham.productservice.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {
    Product save(Product product);
    List<Product>  findAll();

    Product findByIdIs(Long id);
    void deleteById(Long id);

    List<Product> findByCategoryTitle(String category);
}
