package com.ecommerce.shopease.controllers;

import com.ecommerce.shopease.models.Category;
import com.ecommerce.shopease.repos.CategoryRepository;
import com.ecommerce.shopease.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }
}
