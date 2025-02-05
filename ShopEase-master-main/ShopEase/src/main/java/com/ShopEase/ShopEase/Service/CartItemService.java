package com.ShopEase.ShopEase.Service;

import com.ShopEase.ShopEase.Model.CartItem;
import com.ShopEase.ShopEase.Repository.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartItemService {

    @Autowired
    private CartItemRepository cartItemRepository;

    // Method to retrieve all cart items
    public List<CartItem> getAllCartItems() {
        return cartItemRepository.findAll();
    }

    // Method to retrieve a cart item by ID
    public Optional<CartItem> getCartItemById(Long id) {
        return cartItemRepository.findById(id);
    }

    // Method to create a new cart item
    public CartItem createCartItem(CartItem cartItem) {
        return cartItemRepository.save(cartItem);
    }

    // Method to delete a cart item by ID
    public boolean deleteCartItem(Long id) {
        if (cartItemRepository.existsById(id)) {
            cartItemRepository.deleteById(id);
            return true;
        } else {
            return false;
        }

    }
}
