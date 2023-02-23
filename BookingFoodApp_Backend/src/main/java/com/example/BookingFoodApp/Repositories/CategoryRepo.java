package com.example.BookingFoodApp.Repositories;

import com.example.BookingFoodApp.Entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

@Repository
public interface CategoryRepo extends JpaRepository<Category,Integer> {
}
