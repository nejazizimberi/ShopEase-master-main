package com.ShopEase.ShopEase.Controller;

import com.ShopEase.ShopEase.DTO.CartDTO;
import com.ShopEase.ShopEase.Model.Cart;
import com.ShopEase.ShopEase.Service.CartService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.*;

@ExtendWith(MockitoExtension.class)
class CartControllerTest {

    @Mock
    private CartService cartService;

    @InjectMocks
    private CartController cartController;

    @Test
    void testGetCartByUserId_Success() {
        Long userId = 1L;
        Cart mockCart = new Cart();
        mockCart.setId(10L);

        when(cartService.getCartByUserId(userId)).thenReturn(mockCart);

        ResponseEntity<Cart> response = cartController.getCartByUserId(userId);

        assertNotNull(response);
        assertEquals(OK, response.getStatusCode());
        assertEquals(mockCart, response.getBody());

        verify(cartService, times(1)).getCartByUserId(userId);
    }

    @Test
    void testCreateCart_Success() {
        CartDTO cartDTO = new CartDTO();
        cartDTO.setUserId(1L);

        when(cartService.createCart(any(CartDTO.class))).thenReturn(cartDTO);

        ResponseEntity<CartDTO> response = cartController.createCart(cartDTO);

        assertNotNull(response);
        assertEquals(CREATED, response.getStatusCode());
        assertEquals(cartDTO, response.getBody());

        verify(cartService, times(1)).createCart(cartDTO);
    }

    @Test
    void testDeleteCart_Success() {
        Long cartId = 10L;
        doNothing().when(cartService).deleteCart(cartId);

        ResponseEntity<String> response = cartController.deleteCart(cartId);

        assertEquals(OK, response.getStatusCode());
        assertEquals("Cart deleted successfully.", response.getBody());

        verify(cartService, times(1)).deleteCart(cartId);
    }

    @Test
    void testDeleteCart_NotFound() {
        Long cartId = 99L;
        doThrow(new RuntimeException("Cart not found")).when(cartService).deleteCart(cartId);

        ResponseEntity<String> response = cartController.deleteCart(cartId);

        assertEquals(NOT_FOUND, response.getStatusCode());
        assertEquals("Error: Cart not found", response.getBody());

        verify(cartService, times(1)).deleteCart(cartId);
    }

    @Test
    void testCalculateTotal_Success() {
        Long userId = 1L;
        BigDecimal total = BigDecimal.valueOf(100.50);

        when(cartService.calculateTotal(userId)).thenReturn(total);

        ResponseEntity<BigDecimal> response = cartController.calculateTotal(userId);

        assertNotNull(response);
        assertEquals(OK, response.getStatusCode());
        assertEquals(total, response.getBody());

        verify(cartService, times(1)).calculateTotal(userId);
    }
}
