package com.example.orderms.Services;

import com.example.grpc.product.ProductRequest;
import com.example.orderms.Models.Order;
import com.example.orderms.Models.Product;
import com.example.orderms.Repository.OrderRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public UUID createOrder(Order order) throws BadRequestException {

        // O @Valid nao esta funcionando em Product
        // Provavelmente pq era Embeddable
        for (Product product : order.getProducts())
        {
            if (product.getQuantity() <= 0)
                throw new BadRequestException("Nao pode existir um produto com quantidade 0");
        }

        // Poderia simplesmente receber um DTO no endpoint
        Order newOrder = new Order(order.getProducts());

        for (Product productId : newOrder.getProducts()) {
            if (!productGrpcService.checkProductStock(productId.getProductId(), productId.getQuantity())) {
                throw new BadRequestException("Produto sem estoque, id: " + productId);
            }
        }

        boolean stockUpdated = productGrpcService.updateProductStock(
                newOrder.getId().toString(),
                newOrder.getProducts().stream()
                        .map(productId -> ProductRequest.newBuilder()
                                .setProductId(productId.getProductId())
                                .setQuantity(productId.getQuantity())
                                .build())
                        .collect(Collectors.toList())
        );

        // Em caso de race condition
        if (!stockUpdated) {
            throw new BadRequestException("Falha ao atualizar estoque");
        }

        cursor.save(newOrder);

        return newOrder.getId();
    }

    public Order getOrderById(UUID id) throws BadRequestException {

        return cursor.findById(id).orElseThrow(() -> new BadRequestException("Order not found"));
    }
}
