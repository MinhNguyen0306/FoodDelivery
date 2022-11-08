package com.example.BookingFoodApp.Services.Impl;

import com.example.BookingFoodApp.Dto.UserDto;
import com.example.BookingFoodApp.Entities.User;
import com.example.BookingFoodApp.Exceptions.ResourceNotFoundException;
import com.example.BookingFoodApp.Repositories.UserRepo;
import com.example.BookingFoodApp.Services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = this.dtoToUser(userDto);
        User createdUser = this.userRepo.save(user);
        return this.userToDto(createdUser);
    }

    @Override
    public UserDto updateUser(UserDto userDto, Integer userId) {
        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User","Id",userId));
        User updatedUser = this.userRepo.save(user);
        return this.userToDto(updatedUser);
    }

    @Override
    public void deleteUser(Integer userId) {
        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User","Id",userId));
        this.userRepo.delete(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> userList = this.userRepo.findAll();
        List<UserDto> userDtoList = userList.stream().map(user -> this.userToDto(user)).collect(Collectors.toList());
        return userDtoList;
    }

//    @Override
//    public Optional<UserDto> findByEmailAndPassword(String email, String password) {
//        Optional<User> user = Optional.ofNullable(this.userRepo.findByEmailAndPassword(email, password)
//                .orElseThrow(() -> new ResourceNotFoundException(email)));
//        UserDto userDto = this.userToDto(user.get());
//        return Optional.ofNullable(userDto);
//    }

    @Override
    public List<String> checkUserEmail(String email) {
        return this.userRepo.checkUserEmail(email);
    }

    @Override
    public String checkPasswordByEmail(String email) {
        return this.userRepo.checkUserPasswordByEmail(email);
    }

    @Override
    public UserDto getUserDetailsByEmail(String email) {
        User user = this.userRepo.getUserByEmail(email);
        UserDto userDto = this.userToDto(user);
        return userDto;
    }

    @Override
    public int registerNewUserSeviceMethod(String name, String email, String phone, String password) {
        return this.userRepo.registerNewUser(name, email, phone, password);
    }

    private User dtoToUser(UserDto userDto){
        User user = this.modelMapper.map(userDto, User.class);
        return user;
    }

    public UserDto userToDto(User user) {
        UserDto userDto = this.modelMapper.map(user, UserDto.class);
        return userDto;
    }
}
