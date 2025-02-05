package com.ShopEase.ShopEase.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 4, max = 20)
    private String username;

    @Email
    @NotBlank
    private String email;

    // Relationship with Cart: One user has one cart
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private Cart cart;

    // Relationship with Order: One user can have multiple orders
    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Order> orders = new ArrayList<>(); // Initialize list

    // Getter and Setter for Cart
    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        if (cart == null) {
            if (this.cart != null) {
                this.cart.setUser(null);  // Remove back reference
            }
        } else {
            cart.setUser(this);  // Set back reference
        }
        this.cart = cart;
    }

    // Getter and Setter for orders
    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    // Getters and Setters for user details
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", orders=" + orders +
                '}';
    }
}
