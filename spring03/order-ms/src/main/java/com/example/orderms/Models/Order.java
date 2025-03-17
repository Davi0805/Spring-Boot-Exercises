package com.example.orderms.Models;

import com.example.grpc.product.ProductRequest;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
public class Order {

    @Id
    private UUID id;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @ElementCollection
    private List<Product> products;

    public Order(List<Product> orderProducts)
    {
        this.id = UUID.randomUUID();
        this.products = orderProducts;
        this.status = OrderStatus.PENDING;
    }
}
