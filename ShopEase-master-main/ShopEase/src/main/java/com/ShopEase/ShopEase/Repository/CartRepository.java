package com.ShopEase.ShopEase.Repository;

import com.ShopEase.ShopEase.Model.Cart;
import com.ShopEase.ShopEase.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    // Find a cart by the associated user
    Optional<Cart> findByUser(User user);

    // Find a cart by the userId (you can directly fetch it by userId as well)
    Optional<Cart> findByUserId(Long userId);
}
