package com.example.BookingFoodApp.Entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;

import java.util.*;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "userid")
    private int id;

    @Column(name = "username")
    private String name;

    private String email;

    private String phone;

    @Column(name = "passwordhash")
    private String password;

    @Column(name = "imagename")
    private String image;

    private Date created_at;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<Order> orders = new HashSet<>();


    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable( name = "user_role",
                joinColumns = @JoinColumn(name = "users", referencedColumnName = "userid"),
                inverseJoinColumns = @JoinColumn(name = "role", referencedColumnName = "roleid"))
    private Set<Role> roles = new HashSet<>();
}
