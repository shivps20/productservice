package org.example.evaluations.productservice.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity(name = "products")
public class Product extends BaseModel {
    private String name;
    private String description;
    private Double price;

    @ManyToOne(cascade = CascadeType.ALL)
    private Category category;

    private String image;
}


/*
Cardinality
Product Category => M : 1
Product belongs to one Category
Category can have multiple Products

Here we are going with M:1 , but generally a Product can belong to multiple Categories
 */