package com.example.thedeveloperlife.controller;

import com.example.thedeveloperlife.dto.ApiResponseDto;
import com.example.thedeveloperlife.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/categories")
    public ResponseEntity<ApiResponseDto> getCategories() {
        return categoryService.getCategories();
    }
}
