package com.example.order.Services;

import com.example.order.Models.Order;
import com.example.order.Repository.OrderRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.UUID;

@Service
public class OrderService {

    private final OrderRepository cursor;

    @Autowired
    public OrderService(OrderRepository repository)
    {
        this.cursor = repository;
    }

    public UUID createOrder(Order order) throws RuntimeException
    {

        cursor.saveAndFlush(order);

        // TODO: LOGICA DE MENSAGERIA E CHECAGEM

        return order.getId();
    }

    public List<Order> getAllOrders() throws RuntimeException
    {
        List<Order> orders = cursor.findAll();
        if (orders.isEmpty())
            throw new RuntimeException("Nenhum pedido encontrado!");
        return orders;
    }

}
