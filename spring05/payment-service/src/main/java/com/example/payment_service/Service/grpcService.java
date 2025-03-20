package com.example.payment_service.Service;

import com.example.grpc.product.*;
import com.example.payment_service.DTO.GrpcOrderMessage;
import com.stripe.exception.StripeException;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.stereotype.Service;

@GrpcService
public class grpcService extends ProductServiceGrpc.ProductServiceImplBase {

    private final StripePaymentService paymentService;

    public grpcService(StripePaymentService paymentService)
    {
        this.paymentService = paymentService;
    }

    @Override
    public void getPaymentLink(PaymentRequest request, StreamObserver<OrderPaymentLink> responseObserver)
    {
        String paymentLink;
        OrderPaymentLink response;

        try {
            paymentLink = createPaymentLink(request);
            response = OrderPaymentLink.newBuilder()
                    .setSuccess(true)
                    .setPaymentLink(paymentLink)
                    .build();
        } catch (Exception e) {
            response = OrderPaymentLink.newBuilder()
                    .setSuccess(false)
                    .setPaymentLink("")
                    .build();
        }

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }


    private String createPaymentLink(PaymentRequest request) throws StripeException {
        GrpcOrderMessage order = new GrpcOrderMessage();
        order.setOrderId(request.getOrderId());
        order.setProducts(request.getProductsList());

        return paymentService.createCartPaymentLink(order);
    }
}
