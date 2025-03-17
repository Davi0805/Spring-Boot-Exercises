package com.example.order.Controller;

import com.example.order.DTO.OrderDTO;
import com.example.order.Models.Order;
import com.example.order.Services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService cursor;

    @Autowired
    public OrderController(OrderService cursor)
    {
        this.cursor = cursor;
    }

    @PostMapping
    public ResponseEntity<UUID> createOrder(@RequestBody OrderDTO order)
    {
        try {
            UUID orderId = cursor.createOrder(order);
            return ResponseEntity.ok(orderId);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders()
    {
        try {
            return ResponseEntity.ok(cursor.getAllOrders());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}
