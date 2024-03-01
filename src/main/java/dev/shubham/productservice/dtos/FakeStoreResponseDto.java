package dev.shubham.productservice.dtos;

import dev.shubham.productservice.models.Category;
import dev.shubham.productservice.models.Product;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class FakeStoreResponseDto {
    private long id;
    private String title;
    private double price;;
    private  String category;
    private  String description;
    private String image;

    public Product toProduct(){
        Product product = new Product();
        product.setId(this.id);
        product.setTitle(this.title);
        product.setPrice(this.price);
        product.setDescription(this.description);
        product.setImageUrl(this.image);

        Category category = new Category();
        category.setName(this.category);

        product.setCategory(category);
        return product;
    }

}
