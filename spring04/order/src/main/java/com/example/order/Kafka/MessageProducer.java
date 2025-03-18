package com.example.order.Kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessageProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String topic, String messsage)
    {
        kafkaTemplate.send(topic, messsage);
        System.out.println("topico " + topic + ": " + messsage);
    }
}
