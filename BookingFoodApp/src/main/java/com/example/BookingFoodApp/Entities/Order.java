package com.example.BookingFoodApp.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.aspectj.weaver.ast.Or;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "orders")
@NoArgsConstructor
@Getter
@Setter
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orderid")
    private int id;

    @Column(name = "total_items", nullable = false)
    private int totalItems;

    @Column(name = "total_prices", nullable = false)
    private double totalPrices;

    @Column(name = "orderstatus")
    private int orderstatus;

    @Column(name = "from_address", length = 255)
    private String fromAddress;

    @DateTimeFormat(pattern = "dd-M-yyyy hh:mm:ss")
    private LocalDateTime created_at;

    @DateTimeFormat(pattern = "dd-M-yyyy hh:mm:ss")
    private LocalDateTime updated_at;


    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "userid")
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<OrderItem> orderItems = new HashSet<>();
}
