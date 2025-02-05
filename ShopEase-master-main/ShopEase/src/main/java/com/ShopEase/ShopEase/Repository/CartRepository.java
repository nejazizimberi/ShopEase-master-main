package com.ShopEase.ShopEase.Repository;

import com.ShopEase.ShopEase.Model.Cart;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUser(com.ShopEase.ShopEase.Model.User user);

    // Find a cart by the userId (you can directly fetch it by userId as well)
    Optional<Cart> findByUserId(Long userId);



    // Additional custom methods if necessary
}

