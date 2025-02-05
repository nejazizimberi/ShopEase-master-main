package com.ShopEase.ShopEase.Service;

import com.ShopEase.ShopEase.DTO.CartDTO;
import com.ShopEase.ShopEase.Exception.CartNotFoundException;
import com.ShopEase.ShopEase.Exception.ProductNotFoundException;
import com.ShopEase.ShopEase.Exception.UserNotFoundException;
import com.ShopEase.ShopEase.Model.*;
import com.ShopEase.ShopEase.Repository.CartItemRepository;
import com.ShopEase.ShopEase.Repository.CartRepository;
import com.ShopEase.ShopEase.Repository.ProductRepository;
import com.ShopEase.ShopEase.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;

    @Autowired
    public CartService(CartRepository cartRepository,
                       UserRepository userRepository,
                       ProductRepository productRepository,
                       CartItemRepository cartItemRepository) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.cartItemRepository = cartItemRepository;
    }

    public List<Cart> getAllCarts() {
        return cartRepository.findAll();
    }

    public boolean cartExists(Long id) {
        return cartRepository.existsById(id);
    }

    @Transactional
    public CartDTO createCart(CartDTO cartDTO) {
        User user = userRepository.findById(cartDTO.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Cart cart = new Cart(user);
        cart = cartRepository.save(cart);
        return convertToDTO(cart);
    }

    public Cart getCartByUserId(Long userId) {
        return cartRepository.findByUserId(userId)
                .orElseGet(() -> {
                    User user = userRepository.findById(userId)
                            .orElseThrow(() -> new UserNotFoundException("User not found"));
                    Cart newCart = new Cart(user);
                    return cartRepository.save(newCart);
                });
    }

    @Transactional
    public Cart addItemToCart(Long userId, Long productId, int quantity) {
        Cart cart = getCartByUserId(userId);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setQuantity(quantity);
        cartItem.setCart(cart);

        cart.addCartItem(cartItem);
        return cartRepository.save(cart);
    }



    @Transactional
    public Cart removeItemFromCart(Long userId, Long itemId) {
        Cart cart = getCartByUserId(userId);

        CartItem itemToRemove = cart.getCartItems().stream()
                .filter(item -> item.getId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new ProductNotFoundException("Cart item not found with ID: " + itemId));

        // Remove the item and ensure database deletion
        cart.removeCartItem(itemToRemove);
        cartRepository.save(cart);

        return cart;
    }

    public BigDecimal calculateTotal(Long userId) {
        Cart cart = getCartByUserId(userId);
        return cart.calculateTotalPrice();
    }

    public void deleteCart(Long id) {
        Optional<Cart> cart = cartRepository.findById(id);
        if (cart.isPresent()) {
            System.out.println("Before deletion: " + cart.get());
            cartRepository.deleteById(id);
            System.out.println("After deletion: " + cartRepository.findById(id));
        } else {
            throw new RuntimeException("Cart not found with ID: " + id);
        }
    }




    private CartDTO convertToDTO(Cart cart) {
        CartDTO dto = new CartDTO();
        dto.setUserId(cart.getUser().getId());
        dto.setCartItems(cart.getCartItems());
        return dto;
    }
}
