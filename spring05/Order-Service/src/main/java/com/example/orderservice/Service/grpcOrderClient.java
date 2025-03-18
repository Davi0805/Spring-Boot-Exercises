package com.example.orderservice.Service;

import com.example.grpc.product.ProductRequest;
import com.example.grpc.product.ProductResponse;
import com.example.grpc.product.ProductServiceGrpc;
import com.example.grpc.product.ProductServiceGrpc.*;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class grpcOrderClient {

    @GrpcClient("product-service")
    private ProductServiceBlockingStub productServiceBlockingStub;

    public boolean checkStock(String id, int quantity)
    {
        ProductRequest request = ProductRequest.newBuilder()
                .setProductId(id).setQuantity(quantity).build();

        ProductResponse response = productServiceBlockingStub.checkStock(request);
        return response.getInStock();
    }
}
