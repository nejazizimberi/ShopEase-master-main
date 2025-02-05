package com.ShopEase.ShopEase.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Cart {
    @ManyToMany
    @JoinTable(
            name = "cart_products",
            joinColumns = @JoinColumn(name = "cart_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private Set<Product> products = new HashSet<>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    @ToString.Exclude
    private User user;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @ToString.Exclude
    private Set<CartItem> cartItems = new HashSet<>();

    public Cart(User user) {
        this.user = user;
    }

    /**
     * Add a cart item to the cart.
     */
    public void addItem(CartItem item) {
        cartItems.add(item);
        item.setCart(this);
    }

    /**
     * Remove an item from the cart.
     */
    public void removeItem(CartItem item) {
        cartItems.remove(item);
        item.setCart(null);
    }

    /**
     * Clear all items from the cart.
     */
    public void clear() {
        cartItems.clear();
    }

    /**
     * Calculate the total price of all items in the cart.
     */
    public BigDecimal calculateTotalPrice() {
        return cartItems.stream()
                .map(item -> item.getPriceAtAddition().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
