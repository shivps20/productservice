package org.example.evaluations.productservice.services;

import org.example.evaluations.productservice.exceptions.ProductNotFoundException;
import org.example.evaluations.productservice.models.Product;

import java.util.List;

public interface IProductService {
    Product getProductByName(String name);

//    List<Product> getAllProductsByCategoryId(Long categoryId);

    Product createProduct(String name, String description, Double price, Long categoryId, String image);

    List<Product> getAllProducts();

    Product getSingleProduct(Long id)throws ProductNotFoundException;
}
