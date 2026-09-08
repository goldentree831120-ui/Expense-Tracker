package com.expensetracker.backend.controller;

import com.expensetracker.backend.entity.Category;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Small convenience endpoint so the frontend can populate the category
 * dropdown without hardcoding the enum values in JS.
 */
@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @GetMapping
    public Category[] getAll() {
        return Category.values();
    }
}
