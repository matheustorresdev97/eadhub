package com.matheustorres.eadhub.payment.publishers;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.matheustorres.eadhub.payment.dtos.PaymentCommandDTO;

@Component
@RequiredArgsConstructor
public class PaymentCommandPublisher {

    private final RabbitTemplate rabbitTemplate;

    @Value(value = "${eadhub.broker.exchange.paymentCommandExchange}")
    private String paymentCommandExchange;

    @Value(value = "${eadhub.broker.key.paymentCommandKey}")
    private String paymentCommandKey;

    public void publishPaymentCommand(PaymentCommandDTO paymentCommandDTO) {
        rabbitTemplate.convertAndSend(paymentCommandExchange, paymentCommandKey, paymentCommandDTO);
    }
}
