package com.example.BookingFoodApp.Dto;

import com.example.BookingFoodApp.Entities.Food;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CategoryDto {

    private int id;
    private String title;

    private List<FoodDto> foodList = new ArrayList<>();
}
