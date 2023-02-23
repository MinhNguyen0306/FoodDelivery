package com.example.BookingFoodApp.Dto;

import com.example.BookingFoodApp.Entities.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.query.criteria.internal.expression.function.CurrentTimestampFunction;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDto {

    private int id;
    @NotEmpty
    @NotBlank
    @Size(min=4, message = "Username must be min of 4 charter")
    private String name;

    @Email(message = "Email address is not valid!!")
    private String email;

    @Size(max = 11, message = "Phone length must be less than 11 character")
    private String phone;

    @NotEmpty
    @NotBlank
    @Size(min = 8, max = 16, message = "Password must be min of 8 character and max of 16 character!!")
    private String password;
    private String image;
    private Date created_at;

    private Set<OrderDto> orders = new HashSet<>();

    public UserDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
