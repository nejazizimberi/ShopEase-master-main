package com.ShopEase.ShopEase.Controller;

import com.ShopEase.ShopEase.Model.CartItem;
import com.ShopEase.ShopEase.Service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/cart-items") // Standardized API path
public class CartItemController {

    @Autowired
    private CartItemService cartItemService;

    // ✅ GET all Cart Items
    @GetMapping
    public ResponseEntity<List<CartItem>> getAllCartItems() {
        List<CartItem> cartItems = cartItemService.getAllCartItems();
        if (cartItems.isEmpty()) {
            return ResponseEntity.noContent().build(); // Returns 204 if no cart items
        }
        return ResponseEntity.ok(cartItems);
    }

    // ✅ GET Cart Item by ID
    @GetMapping("/{id}")
    public ResponseEntity<CartItem> getCartItemById(@PathVariable Long id) {
        return cartItemService.getCartItemById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ✅ POST a new Cart Item
    @PostMapping
    public ResponseEntity<CartItem> createCartItem(@RequestBody Map<String, Object> requestBody) {
        Long cartId = ((Number) requestBody.get("cart_id")).longValue();
        Long productId = ((Number) requestBody.get("product_id")).longValue();
        int quantity = (int) requestBody.get("quantity");

        CartItem createdCartItem = cartItemService.createCartItem(cartId, productId, quantity);
        return ResponseEntity.status(201).body(createdCartItem);
    }


    // ✅ DELETE a Cart Item by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCartItem(@PathVariable Long id) {
        if (cartItemService.deleteCartItem(id)) {
            return ResponseEntity.noContent().build(); // 204 if deleted
        }
        return ResponseEntity.notFound().build();
    }
}
