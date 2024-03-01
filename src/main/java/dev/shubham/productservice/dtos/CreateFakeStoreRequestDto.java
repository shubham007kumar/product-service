package dev.shubham.productservice.dtos;

import dev.shubham.productservice.models.Category;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateFakeStoreRequestDto {
    private String title;
    private String description;
    private double price;
    private String imageUrl;
    private String category;
}
