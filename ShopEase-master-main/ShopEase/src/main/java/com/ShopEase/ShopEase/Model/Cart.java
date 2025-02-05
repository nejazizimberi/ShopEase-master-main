package com.ShopEase.ShopEase.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "carts")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore // Prevents circular references
    private User user;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore // Prevents circular references
    private Set<CartItem> cartItems = new HashSet<>();

    // Default constructor
    public Cart() {}

    // Constructor with User
    public Cart(User user) {
        this.user = user;
    }

    // Getter and Setter for 'id'
    public Long getId() {
        return id;
    }

    // Getter and Setter for 'user'
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    // Getter for cartItems as Set (used for data persistence)
    public Set<CartItem> getCartItemsSet() {
        return cartItems;
    }

    // Getter for cartItems as List (for returning data)
    public List<CartItem> getCartItems() {
        return cartItems.stream().collect(Collectors.toList());
    }

    // Setter for 'cartItems'
    public void setCartItems(Set<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    // Adds a CartItem to the Cart
    public void addCartItem(CartItem cartItem) {
        cartItems.add(cartItem);
        cartItem.setCart(this);  // Ensures the relationship is set correctly
    }

    // Removes a CartItem from the Cart
    public void removeCartItem(CartItem cartItem) {
        cartItems.remove(cartItem);
        cartItem.setCart(null); // Breaks the relationship before deletion
    }

    // Calculates the total price of items in the cart
    public BigDecimal calculateTotalPrice() {
        return cartItems.stream()
                .map(item -> item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void setId(long l) {
    }
}
