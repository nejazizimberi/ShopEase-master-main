package com.ShopEase.ShopEase.Service;

import com.ShopEase.ShopEase.Model.Cart;
import com.ShopEase.ShopEase.Model.CartItem;
import com.ShopEase.ShopEase.Model.Product;
import com.ShopEase.ShopEase.Repository.CartItemRepository;
import com.ShopEase.ShopEase.Repository.CartRepository;
import com.ShopEase.ShopEase.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartItemService {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository; // If you have a ProductRepository

    // Constructor
    public CartItemService(CartRepository cartRepository, CartItemRepository cartItemRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
    }

    // Method to add item to the cart
    public void addItemToCart(Long userId, Long productId, int quantity) {
        Cart cart = getCartByUserId(userId);
        if (cart == null) {
            // You can handle cart creation here if no cart is found.
            throw new RuntimeException("Cart not found for user: " + userId);
        }
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));

        CartItem cartItem = new CartItem(cart, product, quantity);
        cartItemRepository.save(cartItem);
    }

    // Method to retrieve cart by userId (Assuming Cart is linked with a user)
    private Cart getCartByUserId(Long userId) {
        return cartRepository.findByUserId(userId).orElse(null);
    }

    // Method to retrieve all cart items
    public List<CartItem> getAllCartItems() {
        return cartItemRepository.findAll();
    }

    // Method to retrieve a cart item by ID
    public Optional<CartItem> getCartItemById(Long id) {
        return cartItemRepository.findById(id);
    }

    // Method to create a new cart item
    public void deleteCart(Long id) {
        cartRepository.deleteById(id);
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

    public CartItem createCartItem(Long cartId, Long productId, int quantity) {

        return null;
    }
}
