package com.example.BookingFoodApp.Dto;

import com.example.BookingFoodApp.Entities.Food;
import com.example.BookingFoodApp.Entities.Order;
import com.example.BookingFoodApp.Entities.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderItemDto {
    private int id;
    private int quantity;
    private double totalPrice;
    private FoodDto food;
}
