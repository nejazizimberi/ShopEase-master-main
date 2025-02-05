package com.ShopEase.ShopEase.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when an order is not found.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)  // Automatically returns 404 Not Found
public class OrderNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final Long orderId;
    private final String userMessage;

    /**
     * Constructor with only an error message.
     *
     * @param message The error message.
     */
    public OrderNotFoundException(String message) {
        super(message);
        this.orderId = null;
        this.userMessage = null;
    }

    /**
     * Constructor with an error message and order ID.
     *
     * @param orderId The missing order's ID.
     */
    public OrderNotFoundException(Long orderId) {
        super(String.format("Order with ID %d not found.", orderId));
        this.orderId = orderId;
        this.userMessage = null;
    }

    /**
     * Constructor with an error message, order ID, and a custom user message.
     *
     * @param orderId     The missing order's ID.
     * @param userMessage A custom message for the user.
     */
    public OrderNotFoundException(Long orderId, String userMessage) {
        super(String.format("Order with ID %d not found. %s", orderId, userMessage));
        this.orderId = orderId;
        this.userMessage = userMessage;
    }

    /**
     * Retrieves the order ID.
     *
     * @return The missing order's ID, or null if not provided.
     */
    public Long getOrderId() {
        return orderId;
    }

    /**
     * Retrieves the user message.
     *
     * @return The user message, or null if not provided.
     */
    public String getUserMessage() {
        return userMessage;
    }

    @Override
    public String toString() {
        return String.format("OrderNotFoundException{message='%s', orderId=%s, userMessage='%s'}",
                super.getMessage(),
                orderId != null ? orderId : "N/A",
                userMessage != null ? userMessage : "N/A"
        );
    }
}
