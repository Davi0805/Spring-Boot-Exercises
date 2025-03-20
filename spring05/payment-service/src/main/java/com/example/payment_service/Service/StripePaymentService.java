package com.example.payment_service.Service;

import com.example.grpc.product.ProductPaymentRequest;
import com.example.payment_service.DTO.GrpcOrderMessage;
import com.example.payment_service.DTO.KafkaOrderMessageDTO;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StripePaymentService {

    @Value("${stripe.token}")
    private String key;

    public SessionCreateParams.LineItem.PriceData generatePrice(double price, String nome)
    {
        return SessionCreateParams.LineItem.PriceData.builder()
                .setCurrency("usd")
                .setUnitAmount((long) (price * 100)) // Stripe usa centavos
                .setProductData(
                        SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                .setName(nome)
                                .build()
                )
                .build();
    }


    public String createCartPaymentLink(GrpcOrderMessage order) throws StripeException
    {
        Stripe.apiKey = key;

        List<SessionCreateParams.LineItem> items = new ArrayList<>();
        for (ProductPaymentRequest item : order.getProducts()) {
            items.add(
                    SessionCreateParams.LineItem.builder()
                            .setQuantity((long) item.getQuantity())
                            .setPriceData(generatePrice(item.getUnitPrice(), item.getProductName()))
                            .build()
            );
        }

        SessionCreateParams config = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:8083/payment/" + order.getOrderId()) // adicionar webhooks para callback
                .setCancelUrl("http://localhost:8083/payment/failure/" + order.getOrderId())
                // TODO: Substituir por addAllLineItems quando modificar kafkaOrderMessage
                .addAllLineItem(items)
                .build();

        Session session = Session.create(config);

        return session.getUrl();
    }
}
