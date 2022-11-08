package com.example.BookingFoodApp.Services;

import com.example.BookingFoodApp.Dto.OrderDto;
import com.example.BookingFoodApp.Dto.OrderItemDto;
import com.example.BookingFoodApp.Dto.UserDto;
import com.example.BookingFoodApp.Entities.Order;
import com.example.BookingFoodApp.Entities.User;
import com.example.BookingFoodApp.Payloads.ApiResponse;
import org.aspectj.weaver.ast.Or;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

public interface OrderService {

    // Cart Service
    List<OrderDto> getAllOrders();


    OrderDto addItemToCart(Integer userId, Integer foodId, Integer quantity);

    OrderDto getOrderByUserId(Integer userId); 

    // Môt phát xóa luôn item trong giỏ hàng
    ApiResponse deleteCartItem(Integer userId, Integer itemId);

    // Cập nhật số lượng của item, nếu số lượng giảm về 0 thì xóa item đó luôn
    void updateCartItemByQuantity(Integer userId, Integer itemId, Integer quantity);

    // Đặt mua cart
    OrderDto updateCartByStatus(OrderDto orderDto, Integer orderId);


    // Xóa luôn cái giỏ hàng
    void deleteCart(Integer userId);

    // Chuyển trạng thái order
    ApiResponse changeStateOrder(Integer orderId);
}
