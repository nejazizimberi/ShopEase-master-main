
package com.ShopEase.ShopEase.Service;

import com.ShopEase.ShopEase.Exception.CartNotFoundException;
import com.ShopEase.ShopEase.Exception.UserNotFoundException;
import com.ShopEase.ShopEase.Model.Cart;
import com.ShopEase.ShopEase.Model.User;
import com.ShopEase.ShopEase.Repository.CartRepository;
import com.ShopEase.ShopEase.Repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
        import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {

    @Mock
    private CartRepository cartRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CartService cartService;

    private User user;
    private Cart cart;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setUsername("testUser");

        cart = new Cart(user);
        cart.setId(1L);
    }

    @Test
    void testGetCartByUserId_CartExists() {
        when(cartRepository.findByUserId(user.getId())).thenReturn(Optional.of(cart));

        Cart result = cartService.getCartByUserId(user.getId());

        assertNotNull(result);
        assertEquals(cart.getId(), result.getId());
        verify(cartRepository, times(1)).findByUserId(user.getId());
    }

    @Test
    void testGetCartByUserId_CartDoesNotExist() {
        when(cartRepository.findByUserId(user.getId())).thenReturn(Optional.empty());
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(cartRepository.save(any(Cart.class))).thenReturn(cart);

        Cart result = cartService.getCartByUserId(user.getId());

        assertNotNull(result);
        assertEquals(cart.getId(), result.getId());
        verify(cartRepository, times(1)).save(any(Cart.class));
    }

    @Test
    void testGetCartByUserId_UserNotFound() {
        when(cartRepository.findByUserId(user.getId())).thenReturn(Optional.empty());
        when(userRepository.findById(user.getId())).thenReturn(Optional.empty());

        Exception exception = assertThrows(UserNotFoundException.class, () -> cartService.getCartByUserId(user.getId()));

        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void testDeleteCart_CartExists() {
        when(cartRepository.findById(cart.getId())).thenReturn(Optional.of(cart));
        doNothing().when(cartRepository).deleteById(cart.getId());

        assertDoesNotThrow(() -> cartService.deleteCart(cart.getId()));

        verify(cartRepository, times(1)).deleteById(cart.getId());
    }

    @Test
    void testDeleteCart_CartNotFound() {
        when(cartRepository.findById(cart.getId())).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> cartService.deleteCart(cart.getId()));

        assertEquals("Cart not found with ID: " + cart.getId(), exception.getMessage());
    }
}
