package org.example.evaluations.productservice.dtos;

import lombok.Data;

@Data
public class ProductRequestDto {
    private String name;
    private String description;
    private Double price;
    private Long categoryId;
    private String image;
}
