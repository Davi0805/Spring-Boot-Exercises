package com.example.payment_service.Service;

import com.example.payment_service.DTO.KafkaOrderMessageDTO;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class StripePaymentService {

    @Value("${stripe.token}")
    private String key;

    public String createPaymentLink(KafkaOrderMessageDTO order) throws StripeException
    {
        Stripe.apiKey = key;

        SessionCreateParams config = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("dfsfsdfsdjfl") // adicionar webhooks para callback
                .setCancelUrl("fgkdhfgjhdfkgjhjdfk")
                // TODO: Substituir por addAllLineItems quando modificar kafkaOrderMessage
                .addLineItem(
                        SessionCreateParams.LineItem.builder()
                                .setQuantity(1L)
                                .setPriceData(generatePrice(order.getPrice()))
                                .build()
                )
                .build();

        Session session = Session.create(config);

        return session.getUrl();
    }

    public SessionCreateParams.LineItem.PriceData generatePrice(double price)
    {
        return SessionCreateParams.LineItem.PriceData.builder()
                .setCurrency("usd")
                .setUnitAmount((long) (price * 100)) // Stripe usa centavos
                .setProductData(
                        SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                .setName("NOME de Teste")
                                .build()
                )
                .build();
    }
}
