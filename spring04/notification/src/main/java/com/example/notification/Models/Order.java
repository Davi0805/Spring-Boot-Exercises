package com.example.notification.Models;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
public class Order {

    public Order(List<Product> produtos)
    {

        this.products = produtos;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ElementCollection
    private List<Product> products;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;


    //private LocalDateTime created_at;

//    @Override
//    public String toString()
//    {
//        // Dar fix nisso pois nao esta serializando propriamente o
//        // created_at e products
//        try {
//            return new ObjectMapper().writeValueAsString(this);
//        } catch (Exception e) {
//            System.out.println("ERRO AO DESERIALIZAR: " + e.getMessage());
//            return super.toString();
//        }
//    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("\"id\": \"").append(id).append("\", ");

        sb.append("\"products\": [");
        if (products != null) {
            for (int i = 0; i < products.size(); i++) {
                sb.append(products.get(i).toString());
                if (i < products.size() - 1) sb.append(", ");
            }
        }
        sb.append("], ");

        //sb.append("\"status\": \"").append(status).append("\", ");
        sb.append("\"status\": \"").append(status).append("\"");

//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//        sb.append("\"created_at\": \"").append(created_at.format(formatter)).append("\"");

        sb.append("}");
        return sb.toString();
    }
}
