package com.example.productservice.Grpc;

import com.example.grpc.product.*;
import com.example.productservice.Models.Product;
import com.example.productservice.Repository.ProductRepository;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

@GrpcService
public class GrpcMessageService extends ProductServiceGrpc.ProductServiceImplBase {

    private final ProductRepository cursor;

    @Autowired
    public GrpcMessageService(ProductRepository repo) {
        this.cursor = repo;
    }

    @Override
    public void checkStock(ProductRequest request, StreamObserver<ProductResponse> responseObserver) {
        ProductResponse response;
        try {
            Product temp = cursor.findById(UUID.fromString(request.getProductId()))
                    .orElseThrow(() -> new RuntimeException("Produto nao encontrado"));

            if (temp.getQuantity() < request.getQuantity() && request.getQuantity() > 0)
                throw new RuntimeException("Estoque em falta");

            response = ProductResponse.newBuilder()
                    .setInStock(true)
                    .setPrice(temp.getPrice())
                    .build();
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
            response = ProductResponse.newBuilder()
                    .setInStock(false)
                    .setPrice(0).build();
        }

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void updateStock(OrderRequest request, StreamObserver<OrderResponse> responseObserver)
    {
        OrderResponse response;
        String orderId;
        try {

            for (ProductResponse temp : request.getProductsList())
            {
                // TODO: QUERY DE DECREMENTO NO PRODUCT REPOSITORY
            }

            response = OrderResponse.newBuilder().setSuccess(true).build();

        } catch (Exception e) {
            System.out.println("ERRO: " + e.getMessage());
            response = OrderResponse.newBuilder().setSuccess(false).build();
        }

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
