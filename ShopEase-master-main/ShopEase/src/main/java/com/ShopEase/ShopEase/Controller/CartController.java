package com.ShopEase.ShopEase.Controller;

import com.ShopEase.ShopEase.DTO.CartDTO;
import com.ShopEase.ShopEase.Exception.CartNotFoundException;
import com.ShopEase.ShopEase.Model.Cart;
import com.ShopEase.ShopEase.Service.CartService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/carts")
public class CartController {

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping
    public ResponseEntity<CartDTO> createCart(@RequestBody @Valid CartDTO cartDTO) {
        return new ResponseEntity<>(cartService.createCart(cartDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Cart> getCartByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(cartService.getCartByUserId(userId));
    }

    @PostMapping("/{userId}/add-item")
    public ResponseEntity<Cart> addItemToCart(@PathVariable Long userId, @RequestParam Long productId, @RequestParam int quantity) {
        return ResponseEntity.ok(cartService.addItemToCart(userId, productId, quantity));
    }

    @DeleteMapping("/{userId}/remove-item")
    public ResponseEntity<Cart> removeItemFromCart(@PathVariable Long userId, @RequestParam Long itemId) {
        return ResponseEntity.ok(cartService.removeItemFromCart(userId, itemId));
    }

    @GetMapping("/{userId}/total")
    public ResponseEntity<BigDecimal> calculateTotal(@PathVariable Long userId) {
        return ResponseEntity.ok(cartService.calculateTotal(userId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCart(@PathVariable Long id) {
        try {
            cartService.deleteCart(id);
            return ResponseEntity.ok("Cart deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(404).body("Error: " + e.getMessage());
        }
    }


    @GetMapping
    public List<Cart> getAllCarts() {
        return cartService.getAllCarts();
    }

}
