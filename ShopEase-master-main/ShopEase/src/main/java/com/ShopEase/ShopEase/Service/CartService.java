package com.ShopEase.ShopEase.Service;

import com.ShopEase.ShopEase.DTO.CartDTO;
import com.ShopEase.ShopEase.Exception.CartNotFoundException;
import com.ShopEase.ShopEase.Exception.ProductNotFoundException;
import com.ShopEase.ShopEase.Exception.UserNotFoundException;
import com.ShopEase.ShopEase.Model.*;
import com.ShopEase.ShopEase.Repository.CartRepository;
import com.ShopEase.ShopEase.Repository.ProductRepository;
import com.ShopEase.ShopEase.Repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final ProductService productService;

    @Autowired
    public CartService(CartRepository cartRepository,
                       UserRepository userRepository,
                       ProductService productService,
                       ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.productService = productService;
        this.productRepository = productRepository;
    }

    /**
     * Create a new cart for a user and add products to it.
     */
    @Transactional
    public CartDTO createCart(CartDTO cartDTO) {
        User user = userRepository.findById(cartDTO.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + cartDTO.getUserId()));

        Cart cart = new Cart(user); // Initialize cart for user

        // Add products to cart
        for (Long productId : cartDTO.getProductIds()) {
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new ProductNotFoundException("Product not found: " + productId));

            CartItem item = new CartItem(product, cart, 1, product.getPrice());
            cart.addItem(item);
        }

        return convertToDTO(cartRepository.save(cart));
    }

    /**
     * Get the cart for a specific user.
     */
    public Cart getCartByUserId(Long userId) {
        return cartRepository.findByUserId(userId)
                .orElseThrow(() -> new CartNotFoundException("Cart not found for user ID: " + userId));
    }

    /**
     * Add a product to the cart or update its quantity if it already exists.
     */
    @Transactional
    public Cart addItemToCart(Long userId, Long productId, int quantity) {
        Cart cart = getCartByUserId(userId);
        Product product = productService.getProductById(productId);

        // Check if product is already in cart
        Optional<CartItem> existingItem = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst();

        if (existingItem.isPresent()) {
            existingItem.get().setQuantity(existingItem.get().getQuantity() + quantity);
        } else {
            CartItem newItem = new CartItem(product, cart, quantity, product.getPrice());
            cart.addItem(newItem);
        }

        return cartRepository.save(cart);
    }

    /**
     * Remove an item from the cart.
     */
    @Transactional
    public Cart removeItemFromCart(Long userId, Long itemId) {
        Cart cart = getCartByUserId(userId);

        CartItem itemToRemove = cart.getCartItems().stream()
                .filter(item -> item.getId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new ProductNotFoundException("Cart item not found with ID: " + itemId));

        cart.removeItem(itemToRemove);
        return cartRepository.save(cart);
    }

    /**
     * Calculate the total price of the cart.
     */
    public BigDecimal calculateTotal(Long userId) {
        return getCartByUserId(userId).calculateTotalPrice();
    }

    /**
     * Clear all items from the user's cart.
     */
    @Transactional
    public void clearCart(Long userId) {
        Cart cart = getCartByUserId(userId);
        cart.clear();
        cartRepository.save(cart);
    }

    /**
     * Convert a Cart entity to a DTO.
     */
    private CartDTO convertToDTO(Cart cart) {
        CartDTO dto = new CartDTO();
        dto.setUserId(cart.getUser().getId());
        dto.setProductIds(cart.getCartItems().stream()
                .map(item -> item.getProduct().getId())
                .toList());
        return dto;
    }

    /**
     * Add product to the cart (Alternative method for Many-to-Many)
     */
    // Add product to the cart (Alternative method for Many-to-Many)
    @Transactional
    public Cart addProductToCart(Long userId, Long productId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

        Cart cart = cartRepository.findByUser(user)
                .orElse(new Cart(user));

        // Create a CartItem and add it to the cart
        CartItem cartItem = new CartItem(product, cart, 1, product.getPrice());  // Assuming quantity = 1
        cart.addItem(cartItem);  // Use addItem method

        return cartRepository.save(cart);
    }

    /**
     * Remove a product from the cart (Many-to-Many).
     */
    // Remove product from the cart (Many-to-Many).
    @Transactional
    public Cart removeProductFromCart(Long userId, Long productId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new CartNotFoundException("Cart not found"));

        // Find the CartItem associated with the product
        CartItem itemToRemove = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new ProductNotFoundException("Product not found in cart"));

        // Remove the CartItem from the cart
        cart.removeItem(itemToRemove);

        return cartRepository.save(cart);
    }

}
