package com.example.BookingFoodApp.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "orderitem")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "itemid")
    private int id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "foodid", referencedColumnName = "foodid")
    @JsonBackReference
    private Food food;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "orderid", referencedColumnName = "orderid")
    private Order order;

    private int quantity;

    @Transient
    public double getTotalprice(){
        return this.food.getPrice() * quantity;
    }

    @Transient
    private double totalPrice;



    public void setTotalPrice(double totalPrice) {
        this.totalPrice = getTotalprice();
    }
}
