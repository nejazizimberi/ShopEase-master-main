package com.ShopEase.ShopEase.Repository;

import com.ShopEase.ShopEase.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // Default findById is already available from JpaRepository

    // Custom query method to find products by a list of IDs (if needed)
    List<Product> findByIdIn(List<Long> productIds); // This is the correct way to find multiple products by IDs

    // You can also define other custom queries here if needed
}

