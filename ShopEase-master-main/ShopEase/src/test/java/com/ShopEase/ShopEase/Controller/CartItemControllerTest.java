package com.ShopEase.ShopEase.Controller;

import com.ShopEase.ShopEase.Model.CartItem;
import com.ShopEase.ShopEase.Service.CartItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class CartItemControllerTest {

    @Mock
    private CartItemService cartItemService;

    @InjectMocks
    private CartItemController cartItemController;

    private CartItem cartItem;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        cartItem = new CartItem();
        cartItem.setId(1L);
        cartItem.setQuantity(2);
        // Set product and cart properties here (mock as needed)
    }

    @Test
    public void testGetCartItemById_Success() {
        when(cartItemService.getCartItemById(1L)).thenReturn(Optional.of(cartItem));

        ResponseEntity<CartItem> response = cartItemController.getCartItemById(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cartItem, response.getBody());
    }

    @Test
    public void testGetCartItemById_NotFound() {
        when(cartItemService.getCartItemById(1L)).thenReturn(Optional.empty());

        ResponseEntity<CartItem> response = cartItemController.getCartItemById(1L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testCreateCartItem_Success() {
        CartItem cartItem = new CartItem();
        cartItem.setId(1L);

        Map<String, Object> requestBody = Map.of(
                "cart_id", 1L,
                "product_id", 1L,
                "quantity", 2
        );

        when(cartItemService.createCartItem(1L, 1L, 2)).thenReturn(cartItem);

        ResponseEntity<CartItem> response = cartItemController.createCartItem(requestBody);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(cartItem, response.getBody());
    }

    @Test
    public void testDeleteCartItem_Success() {
        when(cartItemService.deleteCartItem(1L)).thenReturn(true);

        ResponseEntity<Void> response = cartItemController.deleteCartItem(1L);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void testDeleteCartItem_NotFound() {
        when(cartItemService.deleteCartItem(1L)).thenReturn(false);

        ResponseEntity<Void> response = cartItemController.deleteCartItem(1L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
