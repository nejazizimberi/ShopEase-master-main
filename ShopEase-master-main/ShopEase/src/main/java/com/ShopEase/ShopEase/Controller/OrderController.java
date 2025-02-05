package com.ShopEase.ShopEase.Controller;

import com.ShopEase.ShopEase.Model.Order;
import com.ShopEase.ShopEase.DTO.OrderDTO;  // Import the OrderDTO
import com.ShopEase.ShopEase.Service.OrderService;
import com.ShopEase.ShopEase.Exception.OrderNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders") // Base path for all endpoints in this controller
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // Get all orders
    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    // Get order by id
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        try {
            Order order = orderService.getOrderById(id);
            return ResponseEntity.ok(order);
        } catch (OrderNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    // Create a new order
    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody(required = false) OrderDTO orderDTO) {
        if (orderDTO == null) {
            return ResponseEntity.badRequest().body(null);
        }
        Order order = orderService.createOrder(orderDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }



    // Update an existing order
    @PutMapping("/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable Long id, @RequestBody OrderDTO orderDTO) throws OrderNotFoundException {  // Accept OrderDTO
        Order updatedOrder = orderService.updateOrder(id, orderDTO);  // Pass OrderDTO to the service
        return ResponseEntity.ok(updatedOrder);
    }

    // Delete an order by id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        try {
            orderService.deleteOrder(id);
            return ResponseEntity.noContent().build();  // Return 204 No Content if deleted successfully
        } catch (OrderNotFoundException ex) {
            return ResponseEntity.notFound().build();  // Return 404 if the order is not found
        }
    }
}
