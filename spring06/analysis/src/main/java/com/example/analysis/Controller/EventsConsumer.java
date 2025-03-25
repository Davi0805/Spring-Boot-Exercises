package com.example.analysis.Controller;

import com.example.analysis.DTO.AlertDTO;
import com.example.analysis.DTO.TransactionDTO;
import com.example.analysis.Service.KafkaProducer;
import com.example.analysis.Service.TransactionAnalyser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

// Regras a ser implementado para gerar alerta
// 1 - Checar transacao duplicada com
// range de 1-5 min
// 2 - Transacoes acima de 2000


@Component
public class EventsConsumer {

    private final ObjectMapper desserializer;
    private final TransactionAnalyser transactionAnalyser;
    private final KafkaProducer kafka;

    @Autowired
    public EventsConsumer(ObjectMapper desserializer,
                          TransactionAnalyser analyser,
                          KafkaProducer kafka)
    {
        this.desserializer = desserializer;
        this.transactionAnalyser = analyser;
        this.kafka = kafka;
    }

    @KafkaListener(topics = "transactions", groupId = "data-science")
    public void Transactions(String message)
    {
        TransactionDTO transaction = null;

        System.out.println("MENSAGEM CONSUMIDA: " + message);
        try {
            transaction = desserializer
                    .readValue(message, TransactionDTO.class);

            System.out.println("MENSAGEM DESSERIALIZADA: id = "
                    + transaction.getId() + " | userId = "
                    + transaction.getUser_id()
                    + " | amount = "
                    + transaction.getAmount()
                    + " | timestamp = "
                    + transaction.getTimestamp()
                    + " | location = "
                    + transaction.getLocation());

            transactionAnalyser.run(transaction);
        } catch (RuntimeException e) {
            if (transaction != null)
            {
                String reason = null;
                AlertDTO.Severity status = null;

                System.out.println("e.getMessage = " + e.getMessage());

                switch (Integer.valueOf(e.getMessage())) {
                    case 1: {
                        status = AlertDTO.Severity.HIGH;
                        reason = "Transacao duplicada!";
                        break;
                    }
                    case 2:
                    {
                        status = AlertDTO.Severity.LOW;
                        reason = "Transacao acima do limite!";
                        break;
                    }
                }


                kafka.sendMessage("alerts", new AlertDTO(transaction.getId(), transaction.getUser_id(),
                        reason, status).toString());
            }

            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }
}

// {"id": "0d1e4ed6-fc62-4e4b-a3c3-b2a98321adb7","user_id": "a4e20537-bfe3-42e3-8c63-0a4796bc6a6c","amount": 1305.00,"timestamp": "2024-03-24T12:00:00Z","location": "São Paulo, BR"}
// {"id": "0d1e4ed6-fc62-4e4b-a3c3-b2a98321adb7","user_id": "a2e20537-bfe3-42e3-8c63-0a4796bc6a6c","amount": 1700.00,"timestamp": "2024-03-24T12:00:00Z","location": "São Paulo, BR"}
// {"id": "0d1e4ed6-fc62-4e4b-a3c3-b2a98321adb7","user_id": "a1e20537-bfe3-42e3-8c63-0a4796bc6a6c","amount": 2004.00,"timestamp": "2024-03-24T12:00:00Z","location": "São Paulo, BR"}
