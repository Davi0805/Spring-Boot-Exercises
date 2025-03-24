package com.example.payment_service.Service;

import com.example.grpc.product.*;
import com.example.payment_service.DTO.GrpcOrderMessage;
import com.example.payment_service.Models.Payment;
import com.example.payment_service.Models.PaymentMethod;
import com.stripe.exception.StripeException;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@GrpcService
public class grpcService extends ProductServiceGrpc.ProductServiceImplBase {

    private final StripePaymentService paymentService;
    private final PaymentService paymentCursor;

    public grpcService(StripePaymentService paymentService, PaymentService paymentCursor)
    {
        this.paymentService = paymentService;
        this.paymentCursor = paymentCursor;
    }

    @Override
    public void getPaymentLink(PaymentRequest request, StreamObserver<OrderPaymentLink> responseObserver)
    {
        String paymentLink;
        OrderPaymentLink response;
        double totalPrice = 0;

        for(ProductPaymentRequest product : request.getProductsList())
            totalPrice += product.getUnitPrice() * product.getQuantity();

        try {
            paymentLink = createPaymentLink(request);

            Payment register = new Payment(UUID.fromString(request.getOrderId()),
                    totalPrice, PaymentMethod.CREDIT_CARD);

            paymentCursor.createPaymentRegister(register);

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
