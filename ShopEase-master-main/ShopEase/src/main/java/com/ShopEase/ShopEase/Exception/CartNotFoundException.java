package com.ShopEase.ShopEase.Exception;

public class CartNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final Long cartId;
    private final Long userId;

    // Constructor with message only
    public CartNotFoundException(String message) {
        super(message);
        this.cartId = null;
        this.userId = null;
    }

    // Constructor with message and cartId
    public CartNotFoundException(String message, Long cartId) {
        super(message);
        this.cartId = cartId;
        this.userId = null;
    }

    // Constructor with message, cartId, and userId
    public CartNotFoundException(String message, Long cartId, Long userId) {
        super(message);
        this.cartId = cartId;
        this.userId = userId;
    }

    public Long getCartId() {
        return cartId;
    }

    public Long getUserId() {
        return userId;
    }

    @Override
    public String getMessage() {
        // Use the base message and append the details if available
        String message = super.getMessage();

        if (cartId != null) {
            message += " | Cart ID: " + cartId;
        }
        if (userId != null) {
            message += " | User ID: " + userId;
        }

        return message;
    }
}
