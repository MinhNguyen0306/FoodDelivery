package com.example.BookingFoodApp.Dto;

import com.example.BookingFoodApp.Entities.Category;
import com.example.BookingFoodApp.Entities.OrderItem;
import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FoodDto {
    private int id;

    @NotEmpty
    private String title;

    @NotNull
    private float price;

    @NotNull
    private int quantity;
    private String image;
    private String descrip;
}
