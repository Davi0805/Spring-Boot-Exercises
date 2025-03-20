package com.example.payment_service.DTO;

import com.example.grpc.product.ProductPaymentRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class GrpcOrderMessage {

    private String orderId;
    List<ProductPaymentRequest> products;
}
