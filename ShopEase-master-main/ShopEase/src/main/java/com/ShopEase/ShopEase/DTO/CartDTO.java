package com.ShopEase.ShopEase.DTO;

import jakarta.validation.constraints.NotNull;
import com.ShopEase.ShopEase.Model.CartItem;
import java.util.List;

public class CartDTO {

    @NotNull(message = "User ID must not be null")
    private Long userId;
    private List<CartItem> cartItems;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }
}
