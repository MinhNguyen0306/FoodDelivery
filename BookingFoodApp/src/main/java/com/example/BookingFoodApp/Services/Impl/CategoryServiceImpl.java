package com.example.BookingFoodApp.Services.Impl;

import com.example.BookingFoodApp.Dto.CategoryDto;
import com.example.BookingFoodApp.Entities.Category;
import com.example.BookingFoodApp.Exceptions.ResourceNotFoundException;
import com.example.BookingFoodApp.Repositories.CategoryRepo;
import com.example.BookingFoodApp.Services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<CategoryDto> getAllCategories() {
        List<Category> categoryList = this.categoryRepo.findAll();
        List<CategoryDto> categoryDtoList = categoryList.stream().map((category) -> this.categoryToDto(category)).collect(Collectors.toList());
        return categoryDtoList;
    }

    @Override
    public CategoryDto getCategoryById(Integer cateId) {
        Category category = this.categoryRepo.findById(cateId)
                .orElseThrow(() -> new ResourceNotFoundException("Category","Id",cateId));
        return this.categoryToDto(category);
    }

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category createdCategory = this.categoryRepo.save(this.dtoToCategory(categoryDto));
        return this.categoryToDto(createdCategory);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer cateId) {
        Category category = this.categoryRepo.findById(cateId)
                .orElseThrow(() -> new ResourceNotFoundException("Category","Id",cateId));
        Category updatedCategory = this.categoryRepo.save(category);
        return this.categoryToDto(updatedCategory);
    }

    @Override
    public void deleteCategory(Integer cateId) {
        Category category = this.categoryRepo.findById(cateId)
                .orElseThrow(() -> new ResourceNotFoundException("Category","Id",cateId));
        this.categoryRepo.delete(category);
    }

    private Category dtoToCategory(CategoryDto categoryDto) {
        Category category = this.modelMapper.map(categoryDto, Category.class);
        return category;
    }

    public CategoryDto categoryToDto(Category category) {
        CategoryDto categoryDto = this.modelMapper.map(category, CategoryDto.class);
        return categoryDto;
    }
}
