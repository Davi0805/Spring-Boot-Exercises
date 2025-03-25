package com.example.order_service.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {

    private final KafkaTemplate kafka;

    @Autowired
    public KafkaProducer(KafkaTemplate kafkaTemplate)
    {
        this.kafka = kafkaTemplate;
    }

    public void send(String topic, String message)
    {
        kafka.send(topic, message);
    }
}
