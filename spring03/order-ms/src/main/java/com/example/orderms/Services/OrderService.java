package com.example.orderms.Services;

import com.example.grpc.product.ProductRequest;
import com.example.orderms.Models.Order;
import com.example.orderms.Repository.OrderRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderService {

    // USAR ESSE TIPO DE INJEÇÃO DE DEPENDÊNCIA
    // PARA EVITAR PROBLEMAS DE TESTES UNITÁRIOS
    private final OrderRepository cursor;
    private final ProductGrpcService productGrpcService;

    @Autowired
    public OrderService(OrderRepository cursor, ProductGrpcService productGrpcService) {
        this.cursor = cursor;
        this.productGrpcService = productGrpcService;
    }

    public Order createOrder(Order order) throws BadRequestException {

        for (String productId : order.getProducts()) {
            if (!productGrpcService.checkProductStock(productId, 1)) {
                throw new BadRequestException("Produto sem estoque, id: " + productId);
            }
        }

        boolean stockUpdated = productGrpcService.updateProductStock(
                order.getId().toString(),
                order.getProducts().stream()
                        .map(productId -> ProductRequest.newBuilder()
                                .setProductId(productId)
                                .setQuantity(1)
                                .build())
                        .collect(Collectors.toList())
        );

        if (!stockUpdated) {
            throw new BadRequestException("Falha ao atualizar estoque");
        }

        return cursor.save(order);
    }

    public Order getOrderById(UUID id) throws BadRequestException {
        productGrpcService.testGrpc();

        return cursor.findById(id).orElseThrow(() -> new BadRequestException("Order not found"));
    }
}
