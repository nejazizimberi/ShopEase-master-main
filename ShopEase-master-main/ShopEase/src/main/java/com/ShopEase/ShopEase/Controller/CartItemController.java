package com.ShopEase.ShopEase.Controller;

import com.ShopEase.ShopEase.Model.CartItem;
import com.ShopEase.ShopEase.Service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cart-items")
public class CartItemController {

    @Autowired
    private CartItemService cartItemService;

    // GET all Cart Items
    @GetMapping
    public List<CartItem> getAllCartItems() {
        return cartItemService.getAllCartItems();
    }

    // GET Cart Item by ID
    @GetMapping("/{id}")
    public ResponseEntity<CartItem> getCartItemById(@PathVariable Long id) {
        Optional<CartItem> cartItem = cartItemService.getCartItemById(id);
        if (cartItem.isPresent()) {
            return ResponseEntity.ok(cartItem.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // POST a new Cart Item
    @PostMapping
    public ResponseEntity<CartItem> createCartItem(@RequestBody CartItem cartItem) {
        CartItem createdCartItem = cartItemService.createCartItem(cartItem);
        return ResponseEntity.status(201).body(createdCartItem);
    }

    // DELETE a Cart Item by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCartItem(@PathVariable Long id) {
        boolean isDeleted = cartItemService.deleteCartItem(id);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
