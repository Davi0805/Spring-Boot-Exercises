package com.example.product_ms.Services;

import com.example.grpc.product.*;
import com.example.product_ms.Models.ProductModel;
import com.example.product_ms.Repository.ProductRepository;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@GrpcService
public class ProductGrpcService extends ProductServiceGrpc.ProductServiceImplBase {

    private final ProductRepository cursor;

    @Autowired
    public ProductGrpcService(ProductRepository productRepository) {
        this.cursor = productRepository;
    }

    @Override
    public void checkStock(ProductRequest request, StreamObserver<StockResponse> responseObserver) {
        String productId = request.getProductId();
        int quantity = request.getQuantity();

        System.out.println("Dados recebidos pelo Microservico de produtos: ProductId = " + productId + " | Quantidade em estoque = " + quantity);

        Optional<ProductModel> produto = cursor.findById(UUID.fromString(productId));

        StockResponse response;

        if (produto.isPresent() && produto.get().getQuantity() >= quantity) {
            response = StockResponse.newBuilder()
                    .setInStock(true)
                    .build();
        } else {
            response = StockResponse.newBuilder()
                    .setInStock(false)
                    .build();
        }

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void updateStock(OrderRequest request, StreamObserver<OrderResponse> responseObserver)
    {
        String orderId = request.getOrderId();
        List<ProductRequest> products = request.getProductsList();

        OrderResponse response;

        System.out.println("UpdateStock - Order id = " + orderId);
        try {
            for(int i = 0; i < products.size(); i++)
            {
                ProductRequest tempProd = products.get(i);
                System.out.println("ProductRequest: ProductId = " + tempProd.getProductId() + " | Quantity = " + tempProd.getQuantity());
                if (cursor.decrementStock(UUID.fromString(tempProd.getProductId()), tempProd.getQuantity()) == 0)
                    throw new RuntimeException("Falha ao decrementar estoque, produto " + tempProd.getProductId() + "nao se encontra mais disponivel!");
            }
            response = OrderResponse.newBuilder()
                    .setOrderId(orderId)
                    .setSuccess(true)
                    .build();

        } catch (Exception e) {

            // Em producao seria substituido por algum log em json, para uso em grafana e loki, ou ELK
            System.out.println(e.getMessage());

            response = OrderResponse.newBuilder()
                    .setOrderId(orderId)
                    .setSuccess(false)
                    .build();
        }
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
