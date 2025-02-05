package com.ShopEase.ShopEase.Service;

import com.ShopEase.ShopEase.Model.Cart;
import com.ShopEase.ShopEase.Model.CartItem;
import com.ShopEase.ShopEase.Model.Product;
import com.ShopEase.ShopEase.Repository.CartItemRepository;
import com.ShopEase.ShopEase.Repository.CartRepository;
import com.ShopEase.ShopEase.Repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.Optional;

@SpringBootTest
public class CartItemServiceTest {

    @Mock
    private CartItemRepository cartItemRepository;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private CartItemService cartItemService;

    private Cart cart;
    private Product product;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        cart = new Cart();
        cart.setId(1L);

        product = new Product();
        product.setId(1L);
        product.setName("Product 1");
        product.setPrice(BigDecimal.valueOf(100));

        // Mock cartRepository behavior
        when(cartRepository.findByUserId(1L)).thenReturn(Optional.of(cart));
        // Mock productRepository behavior
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
    }

    @Test
    public void testAddItemToCart_Success() {
        cartItemService.addItemToCart(1L, 1L, 2);
        verify(cartItemRepository, times(1)).save(any(CartItem.class));
    }

    @Test
    public void testAddItemToCart_CartNotFound() {
        when(cartRepository.findByUserId(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            cartItemService.addItemToCart(1L, 1L, 2);
        });

        assertEquals("Cart not found for user: 1", exception.getMessage());
    }

    @Test
    public void testDeleteCartItem_Success() {
        CartItem cartItem = new CartItem(cart, product, 2);
        when(cartItemRepository.existsById(1L)).thenReturn(true);

        boolean result = cartItemService.deleteCartItem(1L);
        assertTrue(result);
        verify(cartItemRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteCartItem_NotFound() {
        when(cartItemRepository.existsById(1L)).thenReturn(false);

        boolean result = cartItemService.deleteCartItem(1L);
        assertFalse(result);
    }
}
