package com.example.BookingFoodApp.Controllers;

import com.example.BookingFoodApp.Dto.OrderDto;
import com.example.BookingFoodApp.Entities.Order;
import com.example.BookingFoodApp.Payloads.ApiResponse;
import com.example.BookingFoodApp.Services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/")
    public ResponseEntity<List<OrderDto>> getAllOrders(){
        return ResponseEntity.ok(this.orderService.getAllOrders());
    }

    //
    @PostMapping("/user/{userId}/food/{foodId}")
    public ResponseEntity<String> addItemToCart(@PathVariable Integer userId,
                                                  @PathVariable Integer foodId,
                                                  @RequestParam(value = "quantity", required = false, defaultValue = "1") Integer quantity){
        this.orderService.addItemToCart(userId, foodId, quantity);
        return new ResponseEntity<>("success", HttpStatus.CREATED);
    }

    //Get Order By UserId
    @GetMapping("/user/{userId}")
    public ResponseEntity<OrderDto> getCartUser(@PathVariable Integer userId){
        OrderDto orderDto = this.orderService.getOrderByUserId(userId);
        return new ResponseEntity<>(orderDto, HttpStatus.OK);
    }

    @DeleteMapping("/user/cart/{userId}")
    public ResponseEntity<ApiResponse> deleteCart(@PathVariable Integer userId){
        this.orderService.deleteCart(userId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Deleted cart successfully!", true), HttpStatus.OK);
    }

    // Delete Item In Cart
    @DeleteMapping("/user/{userId}/cart/item/{itemId}")
    public ResponseEntity<ApiResponse> deleteCartItem(@PathVariable Integer userId, @PathVariable Integer itemId){
        ApiResponse deleteApiResponse = this.orderService.deleteCartItem(userId, itemId);
        return new ResponseEntity<ApiResponse>(deleteApiResponse, HttpStatus.OK);
    }

    @PutMapping("/user/cart/{userId}/item/{itemId}")
    public ResponseEntity<ApiResponse> updateCartItemByQuantity(@PathVariable Integer userId,
                                                                @PathVariable Integer itemId,
                                                                @RequestParam("quantity") Integer quantity){
        this.orderService.updateCartItemByQuantity(userId,itemId,quantity);
        return new ResponseEntity<>(new ApiResponse("Updated cart successfully!",true), HttpStatus.OK);
    }
}
