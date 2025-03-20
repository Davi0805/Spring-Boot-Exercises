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
                    .orElseThrow(() -> new RuntimeException("Produto" + request.getProductId() + " nao encontrado"));

            if (temp.getQuantity() < request.getQuantity() && request.getQuantity() > 0)
                throw new RuntimeException("Estoque do produto" + request.getProductId() + " em falta para quantidade " + request.getQuantity());

            response = ProductResponse.newBuilder()
                    .setInStock(true)
                    .setPrice(temp.getPrice())
                    .setProductName(temp.getName())
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
        String orderId = request.getOrderId();
        try {

            for (ProductRequest temp : request.getProductsList())
            {
                if (cursor.decrementStock(UUID.fromString(temp.getProductId()), temp.getQuantity()) == 0)
                    throw new RuntimeException("Falha ao reservar produto!");
            }

            response = OrderResponse.newBuilder().setSuccess(true).build();
            System.out.println("INFO: Pedido " + orderId + " reservado com sucesso!");

        } catch (Exception e) {
            System.out.println("ERRO: " + e.getMessage() + " | OrderId: " + orderId);
            response = OrderResponse.newBuilder().setSuccess(false).build();
        }

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void cancelOrder(OrderRequest request, StreamObserver<OrderCanceledResponse> responseObserver)
    {
        OrderCanceledResponse response;
        String orderId = request.getOrderId();

        try {
            for(ProductRequest temp : request.getProductsList())
            {
                if (cursor.incrementStock(UUID.fromString(temp.getProductId()), temp.getQuantity()) == 0)
                    throw new RuntimeException("Falha ao cancelar e incrementar stock de volta");
            }
            response = OrderCanceledResponse.newBuilder().setSuccess(true).build();
            System.out.println("INFO: Pedido " + orderId + " cancelado com sucesso!");

        } catch (Exception e) {
            System.out.println("ERRO: " + e.getMessage() + " | OrderId: " + orderId);
            response = OrderCanceledResponse.newBuilder().setSuccess(false).build();
        }

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
