package com.ShopEase.ShopEase.Service;

import com.ShopEase.ShopEase.Model.OrderItem;
import com.ShopEase.ShopEase.Repository.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;

    @Autowired
    public OrderItemService(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    // Get all order items
    public List<OrderItem> getAllOrderItems() {
        return orderItemRepository.findAll();
    }

    // Get order item by ID
    public OrderItem getOrderItemById(Long id) {
        return orderItemRepository.findById(id).orElseThrow(() -> new RuntimeException("Order item not found"));
    }

    // Create a new order item
    public OrderItem createOrderItem(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }

    // Update an order item by ID
    public OrderItem updateOrderItem(Long id, OrderItem orderItem) {
        orderItem.setId(id);  // Set the ID of the existing order item
        return orderItemRepository.save(orderItem);
    }

    // Delete an order item by ID
    public void deleteOrderItem(Long id) {
        orderItemRepository.deleteById(id);
    }
}
