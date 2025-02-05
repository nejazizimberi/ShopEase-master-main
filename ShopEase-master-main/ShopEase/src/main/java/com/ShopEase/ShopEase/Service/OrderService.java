package com.ShopEase.ShopEase.Service;

import com.ShopEase.ShopEase.Model.Order;
import com.ShopEase.ShopEase.DTO.OrderDTO;
import com.ShopEase.ShopEase.Model.User;
import com.ShopEase.ShopEase.Repository.OrderRepository;
import com.ShopEase.ShopEase.Repository.UserRepository;
import com.ShopEase.ShopEase.Exception.OrderNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
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
        order.setOrderDate(orderDTO.getOrderDate());
        order.setPrice(orderDTO.getPrice());
        order.setProductName(orderDTO.getProductName());
        order.setUser(userRepository.findById(orderDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + orderDTO.getUserId())));
        order.setTotalPrice(orderDTO.getTotalPrice());
        return orderRepository.save(order);
    }



    // Method to update an existing order
    // Update OrderService class method
    public Order updateOrder(Long id, OrderDTO orderDTO) throws OrderNotFoundException {
        // Fetch the existing order by ID
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with ID: " + id));

        // Directly use LocalDate from orderDTO
        LocalDate orderDate = orderDTO.getOrderDate();
        if (orderDate != null) {
            order.setOrderDate(orderDate);  // Set the LocalDate directly
        }

        // Update other fields
        order.setPrice(orderDTO.getPrice());
        order.setProductName(orderDTO.getProductName());
        order.setTotalPrice(orderDTO.getTotalPrice());

        return orderRepository.save(order); // Save the updated order
    }




    // Method to delete an order
    public void deleteOrder(Long id) throws OrderNotFoundException {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with ID: " + id));

        orderRepository.delete(order);  // delete the order from the database
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
