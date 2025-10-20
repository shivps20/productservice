package org.example.evaluations.productservice.controllers;

import org.example.evaluations.productservice.dtos.CategoryDto;
import org.example.evaluations.productservice.models.Category;
import org.example.evaluations.productservice.services.ICategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    private ICategoryService categoryService;

    public CategoryController(ICategoryService categoryService) {

        this.categoryService = categoryService;
    }

    @GetMapping("/all")
    public List<Category> getCategories() {
        return categoryService.getAllCategories();
    }

    @PostMapping("/create")
    public Category createCategory(@RequestBody CategoryDto categoryDto) {
        return categoryService.createCategory(categoryDto.getName());
    }

    @DeleteMapping("/delete/{id}")
    public void deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
    }

}
