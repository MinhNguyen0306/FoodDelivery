package com.example.BookingFoodApp.Services;

import com.example.BookingFoodApp.Dto.UserDto;
import com.example.BookingFoodApp.Entities.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface UserService {

    UserDto createUser(UserDto userDto);

    UserDto updateUser(UserDto userDto, Integer userId);

    void deleteUser(Integer userId);

    List<UserDto> getAllUsers();

//    Optional<UserDto> findByEmailAndPassword(String email, String password);

    List<String> checkUserEmail(String email);

    String checkPasswordByEmail(String email);

    UserDto getUserDetailsByEmail(String email);

    int registerNewUserSeviceMethod(String name, String email, String phone, String password);
}
