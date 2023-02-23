package com.example.BookingFoodApp.Services;

import com.example.BookingFoodApp.Dto.FoodDto;
import com.example.BookingFoodApp.Payloads.FoodResponse;

import java.util.List;

public interface FoodService {
    FoodDto createFood(FoodDto foodDto);

    FoodDto updateFood(FoodDto foodDto, Integer foodId);

    void deleteFood(Integer foodId);

    FoodDto getFoodById(Integer foodId);

    FoodResponse getAllFood(Integer pageNumer, Integer pageSize, String sortBy, String sortDir);

    List<FoodDto> getFoodsByCategory(Integer cateId);

    List<FoodDto> searchFoods(String keyword);
}
