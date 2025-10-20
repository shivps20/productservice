package org.example.evaluations.productservice.services;

import org.example.evaluations.productservice.models.Category;
import org.example.evaluations.productservice.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements ICategoryService {

    private CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void deleteCategory(Long categoryId) {
        categoryRepository.deleteById(categoryId);
    }

    @Override
    public Category createCategory(String name) {
        /**
         * 1. Check if the category with the given name already exists in the database.
         * 2. If it exists, return the existing category.
         * 3. If it does not exist, create a new Category object with the provided name.
         * 4. Save the new Category object to the database.
         * 5. Return the newly created Category object.
         */

        Optional<Category> existingCategory = categoryRepository.findByNameIgnoreCase(name);
        if (existingCategory.isPresent()) {
            return existingCategory.get();
        }

        Category category = new Category();
        category.setName(name);
        categoryRepository.save(category);
        return category;
    }


    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
}
