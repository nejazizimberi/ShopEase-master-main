package com.ShopEase.ShopEase.Service;

import com.ShopEase.ShopEase.Model.Order;
import com.ShopEase.ShopEase.Model.User;
import com.ShopEase.ShopEase.DTO.OrderDTO;
import com.ShopEase.ShopEase.Repository.OrderRepository;
import com.ShopEase.ShopEase.Repository.UserRepository;
import com.ShopEase.ShopEase.Exception.OrderNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private UserRepository userRepository;

    private Order order;
    private OrderDTO orderDTO;
    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Create a mock User
        user = new User();
        user.setId(1L);
        user.setUsername("user1");

        // Create mock order and orderDTO
        order = new Order();
        order.setId(1L);
        order.setOrderDate(LocalDate.now());
        order.setPrice(BigDecimal.valueOf(100));
        order.setProductName("Product A");
        order.setTotalPrice(BigDecimal.valueOf(100));
        order.setUser(user);

        orderDTO = new OrderDTO();
        orderDTO.setId(1L);
        orderDTO.setOrderDate(LocalDate.now());
        orderDTO.setPrice(BigDecimal.valueOf(100));
        orderDTO.setProductName("Product A");
        orderDTO.setTotalPrice(BigDecimal.valueOf(100));
        orderDTO.setUserId(1L);
    }

    @Test
    void createOrder_ShouldReturnCreatedOrder() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        Order createdOrder = orderService.createOrder(orderDTO);

        assertNotNull(createdOrder);
        assertEquals(order.getId(), createdOrder.getId());
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void updateOrder_ShouldReturnUpdatedOrder() throws OrderNotFoundException {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        Order updatedOrder = orderService.updateOrder(1L, orderDTO);

        assertNotNull(updatedOrder);
        assertEquals(order.getId(), updatedOrder.getId());
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void updateOrder_ShouldThrowOrderNotFoundException() throws OrderNotFoundException {
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(OrderNotFoundException.class, () -> orderService.updateOrder(1L, orderDTO));
    }

    @Test
    void deleteOrder_ShouldDeleteOrder() throws OrderNotFoundException {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        doNothing().when(orderRepository).delete(order);

        orderService.deleteOrder(1L);

        verify(orderRepository, times(1)).delete(order);
    }

    @Test
    void deleteOrder_ShouldThrowOrderNotFoundException() throws OrderNotFoundException {
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(OrderNotFoundException.class, () -> orderService.deleteOrder(1L));
    }

    @Test
    void getOrderById_ShouldReturnOrder() throws OrderNotFoundException {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        Order fetchedOrder = orderService.getOrderById(1L);

        assertNotNull(fetchedOrder);
        assertEquals(order.getId(), fetchedOrder.getId());
    }

    @Test
    void getOrderById_ShouldThrowOrderNotFoundException() throws OrderNotFoundException {
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(OrderNotFoundException.class, () -> orderService.getOrderById(1L));
    }

    @Test
    void getAllOrders_ShouldReturnListOfOrders() {
        when(orderRepository.findAll()).thenReturn(Arrays.asList(order));

        List<Order> orders = orderService.getAllOrders();

        assertNotNull(orders);
        assertFalse(orders.isEmpty());
        assertEquals(1, orders.size());
    }
}
