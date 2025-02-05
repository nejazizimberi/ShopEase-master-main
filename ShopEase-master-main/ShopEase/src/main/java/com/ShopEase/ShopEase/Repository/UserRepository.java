package com.ShopEase.ShopEase.Repository;

import com.ShopEase.ShopEase.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
