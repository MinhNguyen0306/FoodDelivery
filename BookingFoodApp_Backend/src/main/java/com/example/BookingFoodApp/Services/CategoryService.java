package com.example.BookingFoodApp.Services;

import com.example.BookingFoodApp.Dto.CategoryDto;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CategoryService {
    List<CategoryDto> getAllCategories();

    CategoryDto getCategoryById(Integer cateId);
    CategoryDto createCategory(CategoryDto categoryDto);

    CategoryDto updateCategory(CategoryDto categoryDto, Integer cateId);

    void deleteCategory(Integer cateId);
}
