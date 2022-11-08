package com.example.BookingFoodApp.Controllers;

import com.example.BookingFoodApp.Dto.UserDto;
import com.example.BookingFoodApp.Entities.User;
import com.example.BookingFoodApp.Payloads.ApiResponse;
import com.example.BookingFoodApp.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/users")
public class UserController {
    @Autowired
    private UserService userService;


    @GetMapping("/")
    public ResponseEntity<List<UserDto>> getAllUsers(){
        return ResponseEntity.ok(this.userService.getAllUsers());
    }

    @PostMapping("/")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
        UserDto createUser = this.userService.createUser(userDto);
        return new ResponseEntity<>(createUser, HttpStatus.CREATED);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable("userId") Integer uid){
        UserDto updateUser = this.userService.updateUser(userDto,uid);
        return ResponseEntity.ok(updateUser);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") Integer uid){
        this.userService.deleteUser(uid);
        return new ResponseEntity<ApiResponse>(new ApiResponse("User deleted successfully!!", true), HttpStatus.OK);
    }

    //LOGIN USER
    @PostMapping("/user/login")
    public ResponseEntity authenticateUser(@RequestBody UserDto userDto){

        //Get User Email
        List<String> userEmail = this.userService.checkUserEmail(userDto.getEmail());

        //Check If Email is empty
        if(userEmail.isEmpty() || userEmail == null){
            return new ResponseEntity("Email does not exist!", HttpStatus.NOT_FOUND);
        }

        //Get User Password
        String userPassword = this.userService.checkPasswordByEmail(userDto.getEmail());

        //Validate user pasword
        if(!userDto.getPassword().equalsIgnoreCase(userPassword)){
            return new ResponseEntity("Incorrect username or password!", HttpStatus.BAD_REQUEST);
        }

        UserDto detailsUser = this.userService.getUserDetailsByEmail(userDto.getEmail());
        return new ResponseEntity(detailsUser, HttpStatus.OK);
    }

    @PostMapping("/user/register/")
    public ResponseEntity registerNewUser(@RequestParam("name") String name,
                                          @RequestParam("email") String email,
                                          @RequestParam("phone") String phone,
                                          @RequestParam("password") String password){
        List<String> userEmail = this.userService.checkUserEmail(email);
        //Check if Email already exist
        if(userEmail.contains(email)){
            return new ResponseEntity("Email already exist!", HttpStatus.BAD_REQUEST);
        }

        if(name.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty()){
            return new ResponseEntity<>("Please complete all fields!", HttpStatus.BAD_REQUEST);
        }

        String hash_password = BCrypt.hashpw(password, BCrypt.gensalt());
        int result = userService.registerNewUserSeviceMethod(name, email, phone, password);

        if(result != 1){
            return new ResponseEntity<>("failed", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("success", HttpStatus.OK);
    }
}
