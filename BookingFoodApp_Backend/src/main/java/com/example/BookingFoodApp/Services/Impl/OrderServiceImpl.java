package com.example.BookingFoodApp.Services.Impl;

import com.example.BookingFoodApp.Dto.OrderDto;
import com.example.BookingFoodApp.Dto.OrderItemDto;
import com.example.BookingFoodApp.Dto.UserDto;
import com.example.BookingFoodApp.Entities.Food;
import com.example.BookingFoodApp.Entities.Order;
import com.example.BookingFoodApp.Entities.OrderItem;
import com.example.BookingFoodApp.Entities.User;
import com.example.BookingFoodApp.Exceptions.ResourceNotFoundException;
import com.example.BookingFoodApp.Payloads.ApiResponse;
import com.example.BookingFoodApp.Repositories.FoodRepo;
import com.example.BookingFoodApp.Repositories.OrderItemRepo;
import com.example.BookingFoodApp.Repositories.OrderRepo;
import com.example.BookingFoodApp.Repositories.UserRepo;
import com.example.BookingFoodApp.Services.OrderService;
import org.aspectj.weaver.ast.Or;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private FoodRepo foodRepo;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private OrderItemRepo orderItemRepo;


    // Lấy danh sách tất cả order
    @Override
    public List<OrderDto> getAllOrders() {
        List<Order> orders = this.orderRepo.findAll();
        List<OrderDto> orderDtos = orders.stream()
                .map(order -> this.modelMapper.map(order, OrderDto.class)).collect(Collectors.toList());
        return orderDtos;
    }

    // Thêm item vào giỏ hàng
    @Override
    public OrderDto addItemToCart(Integer userId, Integer foodId, Integer quantity) {
        Food food = this.foodRepo.findById(foodId)
                .orElseThrow(() -> new ResourceNotFoundException("Food", "Id", foodId));
        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User","Id", userId));
        Set<Order> orders = user.getOrders();
        Order cart = new Order();

        for (Order o : orders) {
            if(o.getOrderstatus() == 0) {
                cart = o;
            }
        }

        Set<OrderItem> orderItems;
        if(cart.getOrderItems() == null){
            orderItems = new HashSet<>();
            OrderItem orderItem = new OrderItem();
            orderItem.setFood(food);
            orderItem.setQuantity(quantity);
            orderItem.setOrder(cart);
            orderItems.add(orderItem);
            orderItemRepo.save(orderItem);
            cart.setOrderstatus(0);
            cart.setCreated_at(LocalDateTime.now());
        }else {
            orderItems = cart.getOrderItems();
            if(!orderItems.isEmpty()){
                for(OrderItem item : orderItems){
                    if(item.getFood().getId() == foodId){
                        item.setQuantity(item.getQuantity() + quantity);
                        orderItemRepo.save(item);
                        cart.setUpdated_at(LocalDateTime.now());
                    }else{
                        OrderItem orderItem = new OrderItem();
                        orderItem.setFood(food);
                        orderItem.setQuantity(quantity);
                        orderItem.setOrder(cart);
                        orderItems.add(orderItem);
                        orderItemRepo.save(orderItem);
                        cart.setUpdated_at(LocalDateTime.now());
                    }
                }
            }else{
                OrderItem orderItem = new OrderItem();
                orderItem.setQuantity(quantity);
                orderItem.setFood(food);
                orderItem.setOrder(cart);
                orderItemRepo.save(orderItem);
                cart.setUpdated_at(LocalDateTime.now());
            }

        }
        cart.setUser(user);
        cart.setOrderItems(orderItems);
        cart.setFromAddress("ninh hoa");
        cart.setTotalItems(totalItems(orderItems));
        cart.setTotalPrices(totalPrices(orderItems));

        Order cartUpdated = orderRepo.save(cart);
        return this.modelMapper.map(cartUpdated, OrderDto.class);
    }

    @Override
    public OrderDto getOrderByUserId(Integer userId) {
        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User","Id", userId));
        Set<Order> orders = user.getOrders();
        Order cart = null;
        for (Order o : orders) {
            if(o.getOrderstatus() == 0) {
                cart = o;
            }
        }
        return this.modelMapper.map(cart, OrderDto.class);
    }

    // Số lượng item trong giỏ hàng
    private int totalItems(Set<OrderItem> orderItems){
        int totalItems = 0;
        for(OrderItem orderItem : orderItems) {
            totalItems += orderItem.getQuantity();
        }
        return totalItems;
    }

    // Thành tiền giỏ hàng
    private double totalPrices(Set<OrderItem> orderItems){
        double totalPrices = 0.0;
        for(OrderItem orderItem : orderItems){
            totalPrices += orderItem.getTotalprice();
        }
        return totalPrices;
    }

    // Môt phát xóa luôn item trong giỏ hàng
    @Override
    public ApiResponse deleteCartItem(Integer userId, Integer itemId) {
        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User","Id", userId));
        Set<Order> orders = user.getOrders();
        for(Order cart : orders){
            if(cart.getOrderstatus() == 0){
                Set<OrderItem> orderItems = cart.getOrderItems();
                    for (OrderItem item : orderItems){
                        if(item.getId() == itemId){
                            this.orderItemRepo.delete(item);
                        }
                    }
            }else{
                return new ApiResponse("failed", false);
            }
        }
        return new ApiResponse("success", true);
    }

    // Giảm số lượng của item trong giỏ hàng, nếu giảm về 0 thì xóa item đó
    @Override
    public void updateCartItemByQuantity(Integer userId, Integer itemId, Integer quantity) {
        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User","Id", userId));
        Set<Order> orders = user.getOrders();
        Order cart = null;
        for(Order o : orders){
            if(o.getOrderstatus() == 0){
                cart = o;
            }
        }
        Set<OrderItem> orderItems = cart.getOrderItems();
        for (OrderItem item : orderItems){
            if(item.getId() == itemId){
                item.setQuantity(item.getQuantity() - quantity);
                if(item.getQuantity() == 0){
                    this.orderItemRepo.delete(item);
                }else {
                    orderItemRepo.save(item);
                }
            }
        }
        if (cart.getOrderItems().isEmpty()){
            this.orderRepo.delete(cart);
        }
    }

    // Đăt mua cart
    @Override
    public OrderDto updateCartByStatus(OrderDto orderDto, Integer orderId) {
        Order order = this.orderRepo.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order","Id", orderId));
        order.setTotalItems(orderDto.getTotalItems());
        order.setTotalPrices(orderDto.getTotalPrices());
        order.setOrderstatus(1);
        order.setCreated_at(orderDto.getUpdated_at());
        order.setFromAddress(orderDto.getFromAddress());
        Order orderCart = this.orderRepo.save(order);

        OrderDto updatedOrder = this.modelMapper.map(orderCart, OrderDto.class);
        return updatedOrder;
    }

    // Xóa luôn cái giỏ hàng
    @Override
    public void deleteCart(Integer userId) {
        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User","Id", userId));
        Set<Order> orders = user.getOrders();
        for(Order o : orders){
            if(o.getOrderstatus() == 0){
                this.orderRepo.delete(o);
            }
        }
    }

    @Override
    public ApiResponse changeStateOrder(Integer orderId) {
        Order order = this.orderRepo.findById(orderId)
                .orElseThrow(()-> new ResourceNotFoundException("oderId", "Id", orderId));
        if(order.getOrderstatus() == 0){
            order.setOrderstatus(1);
            order.setCreated_at(LocalDateTime.now());
            this.orderRepo.save(order);
        }else if(order.getOrderstatus() == 1){
            order.setOrderstatus(2);
            order.setUpdated_at(LocalDateTime.now());
            this.orderRepo.save(order);
        }else if(order.getOrderstatus() == 2){
            order.setOrderstatus(3);
            order.setUpdated_at(LocalDateTime.now());
            this.orderRepo.save(order);
        }else {
            return new ApiResponse("failed", false);
        }
        return new ApiResponse("success", true);
    }
}
