package com.matheustorres.eadhub.payment.publishers;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.matheustorres.eadhub.payment.dtos.PaymentEventoDTO;

@Component
@RequiredArgsConstructor
public class PaymentEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    @Value(value = "${eadhub.broker.exchange.paymentEvent}")
    private String exchangePaymentEvent;

    public void publishPaymentEvent(PaymentEventoDTO paymentEventoDTO) {
        rabbitTemplate.convertAndSend(exchangePaymentEvent, "", paymentEventoDTO);
    }
}
