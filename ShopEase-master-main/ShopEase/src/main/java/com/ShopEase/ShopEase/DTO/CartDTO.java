package com.ShopEase.ShopEase.DTO;

import jakarta.validation.constraints.NotNull;
import java.util.List;

public class CartDTO {

    @NotNull(message = "User ID must not be null")
    private Long userId;

    // Represents the user associated with the cart
    private List<Long> productIds;  // List of product IDs in the cart

    // Constructor
    public CartDTO() {
        this.userId = userId;
        this.productIds = productIds;
    }

    // Getters and Setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<Long> getProductIds() {
        return productIds;
    }

    public void setProductIds(List<Long> productIds) {
        this.productIds = productIds;
    }

    // Custom toString method for better debugging and logging
    @Override
    public String toString() {
        return "CartDTO{" +
                "userId=" + userId +
                ", productIds=" + productIds +
                '}';
    }
}
