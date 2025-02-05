package com.ShopEase.ShopEase.Repository;

import com.ShopEase.ShopEase.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // No need to implement findById manually
    // Spring Data JPA provides it automatically
}