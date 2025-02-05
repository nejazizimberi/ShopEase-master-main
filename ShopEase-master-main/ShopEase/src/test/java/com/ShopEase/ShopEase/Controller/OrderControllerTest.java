package com.ShopEase.ShopEase.Controller;

import com.ShopEase.ShopEase.Model.Order;
import com.ShopEase.ShopEase.DTO.OrderDTO;
import com.ShopEase.ShopEase.Service.OrderService;
import com.ShopEase.ShopEase.Exception.OrderNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

class OrderControllerTest {

    @InjectMocks
    private OrderController orderController;

    @Mock
    private OrderService orderService;

    private Order order;
    private OrderDTO orderDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Create mock order and orderDTO
        order = new Order();
        order.setId(1L);
        order.setOrderDate(LocalDate.now());
        order.setPrice(BigDecimal.valueOf(100));
        order.setProductName("Product A");
        order.setTotalPrice(BigDecimal.valueOf(100));

        orderDTO = new OrderDTO();
        orderDTO.setId(1L);
        orderDTO.setOrderDate(LocalDate.now());
        orderDTO.setPrice(BigDecimal.valueOf(100));
        orderDTO.setProductName("Product A");
        orderDTO.setTotalPrice(BigDecimal.valueOf(100));
    }

    @Test
    void getAllOrders_ShouldReturnAllOrders() {
        when(orderService.getAllOrders()).thenReturn(Arrays.asList(order));

        ResponseEntity<List<Order>> response = orderController.getAllOrders();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(response.getBody().isEmpty());
    }

    @Test
    void getOrderById_ShouldReturnOrder() throws OrderNotFoundException {
        when(orderService.getOrderById(1L)).thenReturn(order);

        ResponseEntity<Order> response = orderController.getOrderById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(order.getId(), response.getBody().getId());
    }

    @Test
    void getOrderById_ShouldReturnNotFound() throws OrderNotFoundException {
        when(orderService.getOrderById(2L)).thenThrow(OrderNotFoundException.class);

        ResponseEntity<Order> response = orderController.getOrderById(2L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void createOrder_ShouldReturnCreatedOrder() {
        when(orderService.createOrder(orderDTO)).thenReturn(order);

        ResponseEntity<Order> response = orderController.createOrder(orderDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(order.getId(), response.getBody().getId());
    }

    @Test
    void updateOrder_ShouldReturnUpdatedOrder() throws OrderNotFoundException {
        when(orderService.updateOrder(1L, orderDTO)).thenReturn(order);

        ResponseEntity<Order> response = orderController.updateOrder(1L, orderDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(order.getId(), response.getBody().getId());
    }

    @Test
    void updateOrder_ShouldReturnNotFound() throws OrderNotFoundException {
        when(orderService.updateOrder(2L, orderDTO)).thenThrow(OrderNotFoundException.class);

        ResponseEntity<Order> response = orderController.updateOrder(2L, orderDTO);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void deleteOrder_ShouldReturnNoContent() throws OrderNotFoundException {
        doNothing().when(orderService).deleteOrder(1L);

        ResponseEntity<Void> response = orderController.deleteOrder(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void deleteOrder_ShouldReturnNotFound() throws OrderNotFoundException {
        doThrow(OrderNotFoundException.class).when(orderService).deleteOrder(2L);

        ResponseEntity<Void> response = orderController.deleteOrder(2L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
