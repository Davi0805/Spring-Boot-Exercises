package com.example.orderms.Services;

import com.example.grpc.product.*;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductGrpcService {

    @GrpcClient("product-service")
    private ProductServiceGrpc.ProductServiceBlockingStub productServiceBlockingStub;

    public boolean checkProductStock(String productId, int quantity) {
        ProductRequest request = ProductRequest.newBuilder()
                .setProductId(productId)
                .setQuantity(quantity)
                .build();

        StockResponse response = productServiceBlockingStub.checkStock(request);
        return response.getInStock();
    }

    public boolean updateProductStock(String orderId, List<ProductRequest> products) {
        OrderRequest request = OrderRequest.newBuilder()
                .setOrderId(orderId)
                .addAllProducts(products)
                .build();

        OrderResponse response = productServiceBlockingStub.updateStock(request);
        return response.getSuccess();
    }

    public void testGrpc() {
        System.out.println("gRPC Client rodando!");
    }
}
