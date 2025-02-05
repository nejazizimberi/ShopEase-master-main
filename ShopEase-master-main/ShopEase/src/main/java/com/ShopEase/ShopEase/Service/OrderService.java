package com.ShopEase.ShopEase.Service;

import com.ShopEase.ShopEase.Model.Order;
import com.ShopEase.ShopEase.DTO.OrderDTO;
import com.ShopEase.ShopEase.Repository.OrderRepository;
import com.ShopEase.ShopEase.Repository.UserRepository;
import com.ShopEase.ShopEase.Exception.OrderNotFoundException;
import com.ShopEase.ShopEase.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    public OrderService(OrderRepository orderRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    // Method to create a new order
    public Order createOrder(OrderDTO orderDTO) {
        Order order = new Order();

        // Set the values from DTO
        order.setTotalPrice(orderDTO.getTotalPrice());

        // Convert the Date from orderDTO to LocalDate (if it's not null)
        LocalDate orderDate = orderDTO.getOrderDate() != null ? orderDTO.getOrderDate().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate() : LocalDate.now();

        order.setOrderDate(orderDate);  // Use the converted LocalDate

        // Find the user by ID
        User user = userRepository.findById(orderDTO.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));

        order.setUser(user);  // Set the user in the order


        // Save the order and log the action
        Order createdOrder = orderRepository.save(order);
        logger.info("Created new order with ID: {}", createdOrder.getId());

        return createdOrder;
    }

    // Method to update an existing order
    public Order updateOrder(Long orderId, OrderDTO orderDTO) throws OrderNotFoundException {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + orderId));

        // Validate and update total price
        if (orderDTO.getTotalPrice() < 0) {
            throw new IllegalArgumentException("Total price cannot be negative.");
        }

        // Convert the Date from orderDTO to LocalDate (if it's not null)
        LocalDate orderDate = orderDTO.getOrderDate() != null ? orderDTO.getOrderDate().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate() : LocalDate.now();

        // Update fields from DTO
        order.setTotalPrice(orderDTO.getTotalPrice());
        order.setOrderDate(orderDate);

        // Persist the updated order
        Order updatedOrder = orderRepository.save(order);
        logger.info("Updated order with ID: {}", updatedOrder.getId());
        return updatedOrder;
    }

    // Method to delete an order
    public void deleteOrder(Long orderId) throws OrderNotFoundException {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + orderId));

        orderRepository.delete(order);  // Delete the order from the repository
        logger.info("Deleted order with ID: {}", orderId);
    }

    // Method to get an order by its ID
    public Order getOrderById(Long id) throws OrderNotFoundException {
        Optional<Order> order = orderRepository.findById(id);
        return order.orElseThrow(() -> new OrderNotFoundException("Order not found with ID: " + id));
    }

    // Method to get all orders
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}
