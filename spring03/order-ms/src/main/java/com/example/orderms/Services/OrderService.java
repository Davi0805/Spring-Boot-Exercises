package com.example.orderms.Services;

import com.example.orderms.Models.Order;
import com.example.orderms.Repository.OrderRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OrderService {

    // USAR ESSE TIPO DE INJEÇÃO DE DEPENDÊNCIA
    // PARA EVITAR PROBLEMAS DE TESTES UNITÁRIOS
    private final OrderRepository cursor;
    @Autowired
    public OrderService(OrderRepository cursor) {
        this.cursor = cursor;
    }

    public Order createOrder(Order order) {
        return cursor.save(order);
    }

    public Order getOrderById(UUID id) throws BadRequestException {
        return cursor.findById(id).orElseThrow(() -> new BadRequestException("Order not found"));
    }
}
