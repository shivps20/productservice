package org.example.evaluations.productservice.dtos;

import lombok.Data;
import org.example.evaluations.productservice.models.Category;
import org.example.evaluations.productservice.models.Product;

@Data
public class FakeStoreProductDTO {
    private Long id;
    private String name;
    private String description;
    private double price;
    private String category;
    private String image;

    public Product toProduct(){
        Product product = new Product();
        product.setId(this.id);
        product.setName(this.name);
        product.setDescription(this.description);
        product.setPrice(this.price);
        product.setImage(this.image);

        Category category1 = new Category();
        category1.setName(this.category);

        product.setCategory(category1);

        return product;
    }
}
