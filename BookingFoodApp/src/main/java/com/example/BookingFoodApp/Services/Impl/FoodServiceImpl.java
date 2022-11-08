package com.example.BookingFoodApp.Services.Impl;

import com.example.BookingFoodApp.Dto.FoodDto;
import com.example.BookingFoodApp.Entities.Category;
import com.example.BookingFoodApp.Entities.Food;
import com.example.BookingFoodApp.Exceptions.ResourceNotFoundException;
import com.example.BookingFoodApp.Payloads.FoodResponse;
import com.example.BookingFoodApp.Repositories.CategoryRepo;
import com.example.BookingFoodApp.Repositories.FoodRepo;
import com.example.BookingFoodApp.Services.FoodService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FoodServiceImpl implements FoodService {

    @Autowired
    private FoodRepo foodRepo;
    @Autowired
    private CategoryRepo categoryRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public FoodDto createFood(FoodDto foodDto) {
        Food food = this.dtoToFood(foodDto);
        Food savedFood = foodRepo.save(food);
        return this.foodToDto(savedFood);
    }

    @Override
    public FoodDto updateFood(FoodDto foodDto, Integer foodId) {
        Food food = this.foodRepo.findById(foodId)
                .orElseThrow(() -> new ResourceNotFoundException("Food","id",foodId));
        food.setId(foodDto.getId());
        food.setTitle(foodDto.getTitle());
        food.setPrice(foodDto.getPrice());
        food.setQuantity(foodDto.getQuantity());
        food.setImage(foodDto.getImage());
        food.setDescrip(foodDto.getDescrip());

        Food updatedFood = foodRepo.save(food);
        return this.foodToDto(updatedFood);
    }

    @Override
    public void deleteFood(Integer foodId) {
        Food food = this.foodRepo.findById(foodId).orElseThrow(()->new ResourceNotFoundException("Food","id", foodId));
        this.foodRepo.delete(food);
    }

    @Override
    public FoodDto getFoodById(Integer foodId) {
        Food food = this.foodRepo.findById(foodId)
                .orElseThrow(() -> new ResourceNotFoundException("Food","id",foodId));
        return this.foodToDto(food);
    }

    @Override
    public FoodResponse getAllFood(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable page = PageRequest.of(pageNumber,pageSize, sort);
        Page<Food> pageFood = this.foodRepo.findAll(page);

        List<Food> allFoods = pageFood.getContent();
        List<FoodDto> foodDtos = allFoods.stream().map(food -> this.foodToDto(food)).collect(Collectors.toList());

        FoodResponse foodResponse = new FoodResponse();
        foodResponse.setContent(foodDtos);
        foodResponse.setPageNumber(pageFood.getNumber());
        foodResponse.setPageSize(pageFood.getSize());
        foodResponse.setTotalElements(pageFood.getTotalElements());
        foodResponse.setTotalPages(pageFood.getTotalPages());
        foodResponse.setLastPage(pageFood.isLast());
        return foodResponse;
    }

    @Override
    public List<FoodDto> getFoodsByCategory(Integer cateId) {
        Category category = this.categoryRepo.findById(cateId)
                .orElseThrow(()-> new ResourceNotFoundException("Category","Id",cateId));
        List<Food> foodList = this.foodRepo.findByCategory(category);
        List<FoodDto> foodDtoList = foodList.stream().map((food -> this.foodToDto(food))).collect(Collectors.toList());
        return foodDtoList;
    }

    @Override
    public List<FoodDto> searchFoods(String keyword) {
        List<Food> foodList = this.foodRepo.findByTitle("%" + keyword + "%");
        List<FoodDto> foodDtoList = foodList.stream().map((food -> this.foodToDto(food))).collect(Collectors.toList());
        return foodDtoList;
    }

    private Food dtoToFood(FoodDto foodDto){
        Food food = this.modelMapper.map(foodDto, Food.class);
//        food.setId(foodDto.getId());
//        food.setCateid(foodDto.getCateid());
//        food.setTitle(foodDto.getTitle());
//        food.setPrice(foodDto.getPrice());
//        food.setQuantity(foodDto.getQuantity());
//        food.setDescrip(food.getDescrip());
        return food;
    }

    public FoodDto foodToDto(Food food) {
        FoodDto foodDto = this.modelMapper.map(food, FoodDto.class);
//        foodDto.setId(food.getId());
//        foodDto.setCateid(food.getCateid());
//        foodDto.setTitle(food.getTitle());
//        foodDto.setPrice(food.getPrice());
//        foodDto.setQuantity(food.getQuantity());
//        foodDto.setDescrip(food.getDescrip());
        return foodDto;
    }
}
