package com.example.order.Services;

import com.example.order.Kafka.MessageProducer;
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
    private final MessageProducer msgProducer;

    @Autowired
    public OrderService(OrderRepository repository, MessageProducer producer)
    {
        this.cursor = repository;
        this.msgProducer = producer;
    }

    public UUID createOrder(Order order) throws RuntimeException
    {

        cursor.saveAndFlush(order);

        // TODO: LOGICA DE MENSAGERIA E CHECAGEM
        // Comandos para ajudar a debugar
        // docker exec --workdir /opt/kafka/bin/ -it broker sh
        // ./kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic emailNotification --from-beginning
        msgProducer.sendMessage("emailNotification", "Pedido " + order.getId() + " concluido com sucesso!");

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
