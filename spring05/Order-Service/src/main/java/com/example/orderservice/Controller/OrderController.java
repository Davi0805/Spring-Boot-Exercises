package com.example.orderservice.Controller;

import com.example.grpc.product.ProductResponse;
import com.example.orderservice.DTO.KafkaOrderMessageDTO;
import com.example.orderservice.Models.Order;
import com.example.orderservice.Models.OrderStatus;
import com.example.orderservice.Models.Product;
import com.example.orderservice.DTO.OrderInDTO;
import com.example.orderservice.Redis.JwtSession;
import com.example.orderservice.Repository.JwtSessionRepository;
import com.example.orderservice.Service.KafkaProducerService;
import com.example.orderservice.Service.OrderService;
import com.example.orderservice.Service.grpcOrderClient;
import com.example.orderservice.Util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/order")
public class OrderController {
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    private final JwtSessionRepository redisCursor;
    private final grpcOrderClient grpcClient;
    private final JwtUtil jwt;
    private final OrderService cursor;
    private final KafkaProducerService kafka;

    @Autowired
    public OrderController(JwtSessionRepository redisCursor, grpcOrderClient client,
                           JwtUtil jwt, OrderService orderCursor, KafkaProducerService kafka) {
        this.grpcClient = client;
        this.redisCursor = redisCursor;
        this.jwt = jwt;
        this.cursor = orderCursor;
        this.kafka = kafka;
    }

    // TODO: REFAC FOCADO EM DESACOPLAMENTO

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestHeader("Authorization") String token,
                                         @RequestBody OrderInDTO order) {
        token = extractToken(token);
        JwtSession session;
        UUID orderId;
        Order req = new Order(order.getItems());
        String paymentLink;
        try {
            session = redisCursor.findById(token).orElseThrow(() -> new RuntimeException("Token não encontrado"));
            if (!jwt.validateJwtToken(session.getId()))
                throw new RuntimeException("Falha ao se autenticar!");

            UUID userId = UUID.fromString(jwt.getUUIDFromToken(session.getId()));

            // Checagem de estoque e soma de preco do pedido
            double totalPrice = 0;
            List<Product> produtos = order.getItems();
            for (Product prodIt : produtos) {
                ProductResponse response = grpcClient.checkStock(String.valueOf(prodIt.getId()), prodIt.getQuantity());
                if (!response.getInStock())
                    throw new RuntimeException("Produto " + prodIt.getId() + " sem estoque dessa quantidade!");
                totalPrice += response.getPrice() * prodIt.getQuantity();
            }

            // Atualiza inventario e checa sucesso em caso de concorrencia
            // Por mais improvavel que seja, em prod pode acontecer
            if (!grpcClient.updateStock(String.valueOf(req.getId()), produtos))
                throw new RuntimeException("Erro ao atualizar inventario!");

            req.setStatus(OrderStatus.PENDING);
            req.setUserId(userId);
            req.setTotalPrice(totalPrice);

            cursor.createOrUpdateOrder(req);

            // Posta mensagem no kafka, porem TODO: TLVZ TRANSFORMAR EM ASYNC
            KafkaOrderMessageDTO orderMessage = new KafkaOrderMessageDTO(req.getId(), userId, session.getEmail(), totalPrice);
            kafka.sendMessage("orders", orderMessage.toString());

            paymentLink = grpcClient.getPaymentLink(String.valueOf(req.getId()), produtos);
        } catch (Exception e) {
            System.out.println("ERRO:" + e.getMessage());
            return ResponseEntity.badRequest().body("ERRO:" + e.getMessage());
        }

        return ResponseEntity.ok(paymentLink);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrder(@RequestHeader("Authorization") String token,
                                      @PathVariable UUID id) {
        JwtSession session;
        UUID userId;
        token = extractToken(token);
        try {
            session = redisCursor.findById(token).orElseThrow(() -> new RuntimeException("Falha ao se autenticar!"));
            if (!jwt.validateJwtToken(session.getId()))
                throw new RuntimeException("Falha ao se autenticar!");
            userId = UUID.fromString(jwt.getUUIDFromToken(session.getId()));

            Order order = cursor.getOrderById(id);
            if (!order.getUserId().equals(userId))
                throw new RuntimeException("Sem permissao para ver esse pedido!");

            return ResponseEntity.ok(order);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private String extractToken(String bearerToken) {
        if (bearerToken != null && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(BEARER_PREFIX.length());
        }
        throw new RuntimeException("Header de autorização inválido");
    }

}
