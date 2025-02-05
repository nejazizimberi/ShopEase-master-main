package com.ShopEase.ShopEase.Controller;

import com.ShopEase.ShopEase.DTO.OrderItemDTO;
import com.ShopEase.ShopEase.Model.OrderItem;
import com.ShopEase.ShopEase.Service.OrderItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class OrderItemControllerTest {

    @InjectMocks
    private OrderItemController orderItemController;

    @Mock
    private OrderItemService orderItemService;

    private OrderItem orderItem;
    private OrderItemDTO orderItemDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Create mock OrderItem and OrderItemDTO
        orderItem = new OrderItem();
        orderItem.setId(1L);
        orderItem.setProductName("Product A");

        orderItemDTO = new OrderItemDTO();
        orderItemDTO.setOrderId(1L);
        orderItemDTO.setProductId(1L);
        orderItemDTO.setQuantity(2);
    }

    @Test
    void createOrderItem_ShouldReturnCreatedOrderItem() {
        when(orderItemService.createOrderItem(any(OrderItemDTO.class))).thenReturn(orderItem);

        ResponseEntity<OrderItem> response = orderItemController.createOrderItem(orderItemDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(orderItem.getId(), response.getBody().getId());
    }

    @Test
    void createOrderItem_ShouldReturnBadRequest_WhenOrderIdOrProductIdIsNull() {
        orderItemDTO.setOrderId(null);

        ResponseEntity<OrderItem> response = orderItemController.createOrderItem(orderItemDTO);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void getOrderItemById_ShouldReturnOrderItem() {
        when(orderItemService.getOrderItemById(1L)).thenReturn(orderItem);

        ResponseEntity<OrderItem> response = orderItemController.getOrderItemById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(orderItem.getId(), response.getBody().getId());
    }

    @Test
    void deleteOrderItem_ShouldReturnNoContent() {
        doNothing().when(orderItemService).deleteOrderItem(1L);

        ResponseEntity<Void> response = orderItemController.deleteOrderItem(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}
