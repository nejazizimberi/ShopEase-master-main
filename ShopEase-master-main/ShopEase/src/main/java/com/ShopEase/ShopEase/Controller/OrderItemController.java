package com.ShopEase.ShopEase.Controller;

import com.ShopEase.ShopEase.DTO.OrderItemDTO;
import com.ShopEase.ShopEase.Model.OrderItem;
import com.ShopEase.ShopEase.Service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order-items")
public class OrderItemController {

    private final OrderItemService orderItemService;
    private Object orderItemDTO;

    @Autowired
    public OrderItemController(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

    // ✅ Get all order items
    @GetMapping
    public ResponseEntity<List<OrderItem>> getAllOrderItems() {
        List<OrderItem> orderItems = orderItemService.getAllOrderItems();
        return ResponseEntity.ok(orderItems);
    }

    // ✅ Get order item by ID
    @GetMapping("/{id}")
    public ResponseEntity<OrderItem> getOrderItemById(@PathVariable Long id) {
        OrderItem orderItem = orderItemService.getOrderItemById(id);
        return ResponseEntity.ok(orderItem);
    }

    // ✅ Add a new order item (Fixed)
    @PostMapping
    public ResponseEntity<OrderItem> createOrderItem(@RequestBody OrderItemDTO orderItemDTO) {
        // Ensure valid data before processing
        if (orderItemDTO.getOrderId() == null || orderItemDTO.getProductId() == null) {
            return ResponseEntity.badRequest().body(null);
        }

        // Call service method with DTO
        OrderItem createdOrderItem = orderItemService.createOrderItem(orderItemDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrderItem);
    }

    // ✅ Update order item by ID
    @PutMapping("/{id}")
    public ResponseEntity<OrderItem> updateOrderItem(@PathVariable Long id, @RequestBody OrderItemDTO orderItemDTO) {
        OrderItem updatedOrderItem = orderItemService.updateOrderItem(id, orderItemDTO);
        return ResponseEntity.ok(updatedOrderItem);
    }

    // ✅ Delete order item by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderItem(@PathVariable Long id) {
        orderItemService.deleteOrderItem(id);
        return ResponseEntity.noContent().build();
    }
}
