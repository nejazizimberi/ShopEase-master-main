package com.ShopEase.ShopEase.Repository;

import com.ShopEase.ShopEase.Model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    // Find CartItems by Cart ID
    Optional<List<CartItem>> findByCartId(Long cartId);

    // Find CartItem by Cart ID and Product ID
    Optional<CartItem> findByCartIdAndProductId(Long cartId, Long productId);

    // You could add more queries related to CartItem, for example:
    // Find CartItems by userId if you have CartId and UserId association properly mapped
}
