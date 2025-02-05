package com.ShopEase.ShopEase.Service;

import com.ShopEase.ShopEase.DTO.OrderItemDTO;
import com.ShopEase.ShopEase.Model.Order;
import com.ShopEase.ShopEase.Model.OrderItem;
import com.ShopEase.ShopEase.Model.Product;
import com.ShopEase.ShopEase.Repository.OrderItemRepository;
import com.ShopEase.ShopEase.Repository.OrderRepository;
import com.ShopEase.ShopEase.Repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

class OrderItemServiceTest {

    @InjectMocks
    private OrderItemService orderItemService;

    @Mock
    private OrderItemRepository orderItemRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private OrderRepository orderRepository;

    private OrderItem orderItem;
    private OrderItemDTO orderItemDTO;
    private Order order;
    private Product product;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Create mock Product
        product = new Product();
        product.setId(1L);
        product.setName("Product A");
        product.setPrice(BigDecimal.valueOf(50));

        // Create mock Order
        order = new Order();
        order.setId(1L);

        // Create mock OrderItem and OrderItemDTO
        orderItem = new OrderItem();
        orderItem.setId(1L);
        orderItem.setOrder(order);
        orderItem.setProduct(product);
        orderItem.setQuantity(2);
        orderItem.setProductName("Product A");
        orderItem.setPrice(BigDecimal.valueOf(50));
        orderItem.setTotalPrice(BigDecimal.valueOf(100));

        orderItemDTO = new OrderItemDTO();
        orderItemDTO.setOrderId(1L);
        orderItemDTO.setProductId(1L);
        orderItemDTO.setQuantity(2);
    }

    @Test
    void createOrderItem_ShouldReturnCreatedOrderItem() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(orderItemRepository.save(any(OrderItem.class))).thenReturn(orderItem);

        OrderItem createdOrderItem = orderItemService.createOrderItem(orderItemDTO);

        assertNotNull(createdOrderItem);
        assertEquals(orderItem.getId(), createdOrderItem.getId());
        assertEquals(orderItem.getTotalPrice(), createdOrderItem.getTotalPrice());
    }

    @Test
    void createOrderItem_ShouldThrowEntityNotFoundException_WhenOrderNotFound() {
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> orderItemService.createOrderItem(orderItemDTO));
    }

    @Test
    void createOrderItem_ShouldThrowEntityNotFoundException_WhenProductNotFound() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> orderItemService.createOrderItem(orderItemDTO));
    }

    @Test
    void updateOrderItem_ShouldReturnUpdatedOrderItem() {
        when(orderItemRepository.findById(1L)).thenReturn(Optional.of(orderItem));
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(orderItemRepository.save(any(OrderItem.class))).thenReturn(orderItem);

        OrderItem updatedOrderItem = orderItemService.updateOrderItem(1L, orderItemDTO);

        assertNotNull(updatedOrderItem);
        assertEquals(orderItem.getId(), updatedOrderItem.getId());
        assertEquals(orderItem.getTotalPrice(), updatedOrderItem.getTotalPrice());
    }

    @Test
    void updateOrderItem_ShouldThrowEntityNotFoundException_WhenOrderItemNotFound() {
        when(orderItemRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> orderItemService.updateOrderItem(1L, orderItemDTO));
    }

    @Test
    void deleteOrderItem_ShouldDeleteOrderItem() {
        when(orderItemRepository.existsById(1L)).thenReturn(true);
        doNothing().when(orderItemRepository).deleteById(1L);

        orderItemService.deleteOrderItem(1L);

        verify(orderItemRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteOrderItem_ShouldThrowEntityNotFoundException_WhenOrderItemNotFound() {
        when(orderItemRepository.existsById(1L)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> orderItemService.deleteOrderItem(1L));
    }

    @Test
    void getOrderItemById_ShouldReturnOrderItem() {
        when(orderItemRepository.findById(1L)).thenReturn(Optional.of(orderItem));

        OrderItem fetchedOrderItem = orderItemService.getOrderItemById(1L);

        assertNotNull(fetchedOrderItem);
        assertEquals(orderItem.getId(), fetchedOrderItem.getId());
    }

    @Test
    void getAllOrderItems_ShouldReturnListOfOrderItems() {
        when(orderItemRepository.findAll()).thenReturn(Arrays.asList(orderItem));

        List<OrderItem> orderItems = orderItemService.getAllOrderItems();

        assertNotNull(orderItems);
        assertFalse(orderItems.isEmpty());
        assertEquals(1, orderItems.size());
    }
}
