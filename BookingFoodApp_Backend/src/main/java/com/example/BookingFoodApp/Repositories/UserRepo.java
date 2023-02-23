package com.example.BookingFoodApp.Repositories;

import com.example.BookingFoodApp.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.beans.Transient;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User,Integer> {

    @Query(value = "SELECT email FROM users WHERE email = :email", nativeQuery = true)
    List<String> checkUserEmail(@Param("email") String email);

    @Query(value = "SELECT passwordhash FROM users WHERE email = :email", nativeQuery = true)
    String checkUserPasswordByEmail(@Param("email") String email);

    @Query(value = "SELECT * FROM users WHERE email = :email", nativeQuery = true)
    User getUserByEmail(@Param("email") String email);

    @Transactional
    @Modifying
    @Query(value = "insert into users(username, email, phone, passwordhash) values(:name, :email, :phone, :password)", nativeQuery = true)
    int registerNewUser(@Param("name") String name,
                        @Param("email") String email,
                        @Param("phone") String phone,
                        @Param("password") String password);
}
