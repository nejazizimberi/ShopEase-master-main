package com.ShopEase.ShopEase.Repository;

import com.ShopEase.ShopEase.Model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
