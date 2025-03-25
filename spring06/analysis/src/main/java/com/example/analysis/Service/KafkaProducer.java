package com.example.analysis.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {

    private final KafkaTemplate cursor;

    @Autowired
    public KafkaProducer(KafkaTemplate cursor)
    {
        this.cursor = cursor;
    }

    public void sendMessage(String topic, String message)
    {
        cursor.send(topic, message);
    }
}
