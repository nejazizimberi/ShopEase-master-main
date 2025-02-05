package com.ShopEase.ShopEase.Service;

import com.ShopEase.ShopEase.DTO.OrderItemDTO;
import com.ShopEase.ShopEase.Model.Order;
import com.ShopEase.ShopEase.Model.OrderItem;
import com.ShopEase.ShopEase.Model.Product;
import com.ShopEase.ShopEase.Repository.OrderItemRepository;
import com.ShopEase.ShopEase.Repository.OrderRepository;
import com.ShopEase.ShopEase.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityNotFoundException;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    @Autowired
    public OrderItemService(OrderItemRepository orderItemRepository, ProductRepository productRepository, OrderRepository orderRepository) {
        this.orderItemRepository = orderItemRepository;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }

    // Get all order items
    public List<OrderItem> getAllOrderItems() {
        return orderItemRepository.findAll();
    }

    // Get order item by ID
    public OrderItem getOrderItemById(Long id) {
        return orderItemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("OrderItem with ID " + id + " not found"));
    }

    // Create a new order item using orderId and productId
    public OrderItem createOrderItem(Long orderId, Long productId, int quantity) {
        // Ensure the order exists
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order with ID " + orderId + " not found"));

        // Ensure the product exists
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product with ID " + productId + " not found"));

        // Create the OrderItem and set values
        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(order);
        orderItem.setProduct(product);
        orderItem.setQuantity(quantity);
        orderItem.setProductName(product.getName());
        orderItem.setPrice(product.getPrice());
        orderItem.setTotalPrice(product.getPrice().multiply(BigDecimal.valueOf(quantity)));

        // Save the OrderItem
        return orderItemRepository.save(orderItem);
    }

    // Create order item from DTO
    public OrderItem createOrderItem(OrderItemDTO orderItemDTO) {
        return createOrderItem(orderItemDTO.getOrderId(), orderItemDTO.getProductId(), orderItemDTO.getQuantity());
    }

    // Update an order item
    public OrderItem updateOrderItem(Long id, OrderItemDTO orderItemDTO) {
        OrderItem existingOrderItem = orderItemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("OrderItem with ID " + id + " not found"));

        // Ensure the product exists
        Product product = productRepository.findById(orderItemDTO.getProductId())
                .orElseThrow(() -> new EntityNotFoundException("Product with ID " + orderItemDTO.getProductId() + " not found"));

        existingOrderItem.setProduct(product);
        existingOrderItem.setQuantity(orderItemDTO.getQuantity());
        existingOrderItem.setProductName(product.getName());
        existingOrderItem.setPrice(product.getPrice());
        existingOrderItem.setTotalPrice(product.getPrice().multiply(BigDecimal.valueOf(orderItemDTO.getQuantity())));

        return orderItemRepository.save(existingOrderItem);
    }

    // Delete an order item by ID
    public void deleteOrderItem(Long id) {
        if (!orderItemRepository.existsById(id)) {
            throw new EntityNotFoundException("OrderItem with ID " + id + " not found.");
        }
        orderItemRepository.deleteById(id);
    }
}
