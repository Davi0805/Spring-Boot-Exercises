package com.example.orderservice.Service;

import com.example.grpc.product.*;
import com.example.grpc.product.ProductServiceGrpc.*;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.orderservice.Models.Product;

import java.util.ArrayList;
import java.util.List;

@Service
public class grpcOrderClient {

    @GrpcClient("product-service")
    private ProductServiceBlockingStub productServiceBlockingStub;

    public ProductResponse checkStock(String id, int quantity)
    {
        ProductRequest request = ProductRequest.newBuilder()
                .setProductId(id).setQuantity(quantity).build();

        ProductResponse response = productServiceBlockingStub.checkStock(request);
        return response;
    }

    public boolean updateStock(String id, List<Product> products)
    {
        List<ProductRequest> productsList = new ArrayList<>();
        for (Product produto : products)
        {
            productsList.add(ProductRequest.newBuilder().setProductId(String.valueOf(produto.getId())).setQuantity(produto.getQuantity()).build());
        }

        OrderRequest request = OrderRequest.newBuilder().setOrderId(id).addAllProducts(productsList).build();

        OrderResponse response = productServiceBlockingStub.updateStock(request);

        return response.getSuccess();
    }
}
