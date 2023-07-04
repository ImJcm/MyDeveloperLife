package com.example.thedeveloperlife.service;

import com.example.thedeveloperlife.dto.ApiResponseDto;
import com.example.thedeveloperlife.dto.CategoryResponseDto;
import com.example.thedeveloperlife.entity.Category;
import com.example.thedeveloperlife.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;


    public ResponseEntity<ApiResponseDto> getCategories() {
        List<Category> categoryList = categoryRepository.findAllByOrderByIdAsc();

        List<CategoryResponseDto> newCategoryList = categoryList.stream().map(CategoryResponseDto::new).toList();

        return ResponseEntity.status(200).body(new ApiResponseDto(HttpStatus.OK.value(), "전체 카테고리 조회 성공", newCategoryList));
    }
}
