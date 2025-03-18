package com.example.orderservice.Models;

public enum OrderStatus {
    PENDING, // QUANDO O PEDIDO E CRIADO

    PROCESSING, // QUANDO O PEDIDO E PAGG

    CANCELLED // QUANDO O PEDIDO E CANCELADO
                // TIPO QUANDO O PAGAMENTO FALHA
}
