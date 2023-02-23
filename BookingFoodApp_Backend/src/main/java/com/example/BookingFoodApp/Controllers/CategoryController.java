package com.example.BookingFoodApp.Controllers;

import com.example.BookingFoodApp.Dto.CategoryDto;
import com.example.BookingFoodApp.Payloads.ApiResponse;
import com.example.BookingFoodApp.Services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/")
    public ResponseEntity<List<CategoryDto>> getAllCategories(){
        return ResponseEntity.ok(this.categoryService.getAllCategories());
    }

    @GetMapping("/{cateId}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable(name = "cateId") Integer cid){
        CategoryDto categoryDto = this.categoryService.getCategoryById(cid);
        return ResponseEntity.ok(categoryDto);
    }

    @PostMapping("/")
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto) {
        CategoryDto createdCategory = this.categoryService.createCategory(categoryDto);
        return new ResponseEntity<CategoryDto>(createdCategory, HttpStatus.CREATED);
    }

    @PutMapping("/{cateId}")
    public ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto categoryDto, @PathVariable("cateId") Integer cid){
        CategoryDto updatedCategory = this.categoryService.updateCategory(categoryDto,cid);
        return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
    }

    @DeleteMapping("/{cateId}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable("cateId") Integer cid){
        this.categoryService.deleteCategory(cid);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Category deleted successfully!!",true), HttpStatus.OK);
    }
}
