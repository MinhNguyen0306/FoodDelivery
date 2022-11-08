package com.example.BookingFoodApp.Dto;

import com.example.BookingFoodApp.Entities.Food;
import com.example.BookingFoodApp.Entities.OrderItem;
import com.example.BookingFoodApp.Entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderDto {
    private int id;
    private int totalItems;
    private double totalPrices;

    @Size(max = 4, message = "Order only 4 status")
    private int orderstatus;
    private String fromAddress;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;

    private Set<OrderItemDto> orderItems = new HashSet<>();
}
