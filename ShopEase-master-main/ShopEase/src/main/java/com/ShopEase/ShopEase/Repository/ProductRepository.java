package com.ShopEase.ShopEase.Repository;

import com.ShopEase.ShopEase.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // Additional custom queries can be added here if needed
}
