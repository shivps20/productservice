package org.example.evaluations.productservice.services;

import org.example.evaluations.productservice.exceptions.ProductNotFoundException;
import org.example.evaluations.productservice.models.Category;
import org.example.evaluations.productservice.models.Product;
import org.example.evaluations.productservice.repositories.CategoryRepository;
import org.example.evaluations.productservice.repositories.ProductRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Primary
public class ProductServiceImpl implements IProductService {

    private final CategoryRepository categoryRepository;
    private ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Product getProductByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Product name is required");
        }
        Optional<Product> product = productRepository.findByName(name);
        if (product.isPresent()) {
            return product.get();
        }
        return null;
    }

    @Override
    public Product createProduct(String name, String description, Double price, Long categoryId, String image) {
        /**
         * TODO Implement the logic to create and return a new Product
         *
         * 1. Check if the product with the same name already exists (you can assume a method exists for this)
         * 2. If it exists, return the existing product
         * 3. If it does not exist, create a new Product object with the provided details
         * 4. Save the new Product object to the database (you can assume a method exists for this)
         * 5. Return the newly created Product object
         */

        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Product name is required");
        }
        if (price == null || price < 0) {
            throw new IllegalArgumentException("Price must be non-negative");
        }

        Optional<Product> existingProduct = productRepository.findByName(name);
        if (existingProduct.isPresent()) {
            return existingProduct.get();
        }

        // Ensure category exists before creating product
        Category existingCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Category not found: " + categoryId));

        Product newProduct = new Product();
        newProduct.setName(name);
        newProduct.setDescription(description);
        newProduct.setPrice(price);
        newProduct.setCategory(existingCategory);
        newProduct.setImage(image);

        return productRepository.save(newProduct);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getSingleProduct(Long id) throws ProductNotFoundException {
        Optional<Product> product = productRepository.findById(id);

        if(product.isEmpty()) {
            throw new ProductNotFoundException("Product with id " + id + " not found");
        }

        return product.get();
    }
}
