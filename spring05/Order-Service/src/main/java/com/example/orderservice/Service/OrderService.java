package com.example.orderservice.Service;

import com.example.grpc.product.OrderResponse;
import com.example.orderservice.Models.Order;
import com.example.orderservice.Repository.OrderRepository;
import jakarta.persistence.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OrderService {

    private final OrderRepository cursor;

    @Autowired
    public OrderService(OrderRepository repo) {
        this.cursor = repo;
    }

    public void createOrder(Order order) {
        cursor.save(order);
    }

    public Order getOrderById(UUID id)
    {
        return cursor.findById(id).orElseThrow(() -> new RuntimeException("Pedido nao encontrado!"));
    }
}
