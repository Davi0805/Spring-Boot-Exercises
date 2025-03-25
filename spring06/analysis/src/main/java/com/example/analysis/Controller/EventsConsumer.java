package com.example.analysis.Controller;

import com.example.analysis.DTO.TransactionDTO;
import com.example.analysis.Service.TransactionAnalyser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

// Regras a ser implementado para gerar alerta
// 1 - Checar transacao duplicada com
// range de 1-5 min
// 2 - Transacoes acima de 2000
// 3 -  picos (Nao sei oq seriam esses picos ainda)


@Component
public class EventsConsumer {

    private final ObjectMapper desserializer;
    private final TransactionAnalyser transactionAnalyser;

    @Autowired
    public EventsConsumer(ObjectMapper desserializer, TransactionAnalyser analyser)
    {
        this.desserializer = desserializer;
        this.transactionAnalyser = analyser;
    }

    @KafkaListener(topics = "transactions", groupId = "data-science")
    public void Transactions(String message)
    {
        System.out.println("MENSAGEM CONSUMIDA: " + message);
        try {
            TransactionDTO transaction = desserializer
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

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

// {"id": "0d1e4ed6-fc62-4e4b-a3c3-b2a98321adb7","user_id": "a2e20537-bfe3-42e3-8c63-0a4796bc6a6c","amount": 1500.00,"timestamp": "2024-03-24T12:00:00Z","location": "São Paulo, BR"}
// {"id": "0d1e4ed6-fc62-4e4b-a3c3-b2a98321adb7","user_id": "a1e20537-bfe3-42e3-8c63-0a4796bc6a6c","amount": 2004.00,"timestamp": "2024-03-24T12:00:00Z","location": "São Paulo, BR"}
