package org.example.evaluations.productservice.services;

import org.example.evaluations.productservice.models.Category;

import java.util.List;

public interface ICategoryService {
    public void deleteCategory(Long categoryId);

    public Category createCategory(String name);

    List<Category> getAllCategories();
}
