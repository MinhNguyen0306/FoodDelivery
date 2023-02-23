package com.example.BookingFoodApp.Repositories;

import com.example.BookingFoodApp.Entities.Category;
import com.example.BookingFoodApp.Entities.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodRepo extends JpaRepository<Food,Integer> {
    List<Food> findByCategory(Category category);

    @Query("select f from Food f where f.title like :key")
    List<Food> findByTitle(@Param("key") String title);
}
