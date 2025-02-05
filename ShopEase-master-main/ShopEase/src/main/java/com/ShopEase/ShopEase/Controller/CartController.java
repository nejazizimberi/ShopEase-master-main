package com.ShopEase.ShopEase.Controller;

import com.ShopEase.ShopEase.DTO.CartDTO;
import com.ShopEase.ShopEase.Model.Cart;
import com.ShopEase.ShopEase.Service.CartService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/carts")
public class CartController {

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    // Create a new cart
    @PostMapping
    public ResponseEntity<CartDTO> createCart(@RequestBody @Valid CartDTO cartDTO) {
        if (cartDTO.getUserId() == null || cartDTO.getProductIds() == null || cartDTO.getProductIds().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Return 400 if input is invalid
        }

        // Call the service method to create the cart and return CartDTO
        CartDTO createdCart = cartService.createCart(cartDTO);

        // Return the created cart with HTTP status 201 Created
        return new ResponseEntity<>(createdCart, HttpStatus.CREATED);
    }

    // Get cart by user ID
    @GetMapping("/{userId}")
    public ResponseEntity<Cart> getCartByUserId(@PathVariable Long userId) {
        try {
            Cart cart = cartService.getCartByUserId(userId);
            return ResponseEntity.ok(cart);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();  // Cart not found
        }
    }

    // Add an item to the cart
    @PostMapping("/{userId}/add-item")
    public ResponseEntity<Cart> addItemToCart(@PathVariable Long userId, @RequestParam Long productId, @RequestParam int quantity) {
        try {
            Cart updatedCart = cartService.addItemToCart(userId, productId, quantity);
            return ResponseEntity.ok(updatedCart);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Handle error if cart or product is not found
        }
    }

    // Remove an item from the cart
    @DeleteMapping("/{userId}/remove-item")
    public ResponseEntity<Cart> removeItemFromCart(@PathVariable Long userId, @RequestParam Long itemId) {
        try {
            Cart updatedCart = cartService.removeItemFromCart(userId, itemId);
            return ResponseEntity.ok(updatedCart);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Handle error if cart or item is not found
        }
    }

    // Get the total price of items in the cart
    @GetMapping("/{userId}/total")
    public ResponseEntity<BigDecimal> calculateTotal(@PathVariable Long userId) {
        try {
            BigDecimal total = cartService.calculateTotal(userId);
            return ResponseEntity.ok(total);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Handle error if cart is not found
        }
    }

    // Clear the cart for a user
    @DeleteMapping("/{userId}/clear")
    public ResponseEntity<Void> clearCart(@PathVariable Long userId) {
        try {
            cartService.clearCart(userId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // Cart cleared successfully
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Handle error if cart is not found
        }
    }

    // Get all carts (Optional, could be useful for admin functionalities)
    @GetMapping
    public ResponseEntity<String> getAllCarts() {
        return ResponseEntity.ok("All carts fetched successfully.");
    }
}
