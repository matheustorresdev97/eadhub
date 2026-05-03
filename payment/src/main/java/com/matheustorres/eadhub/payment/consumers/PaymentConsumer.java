package com.matheustorres.eadhub.payment.consumers;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.matheustorres.eadhub.payment.dtos.PaymentCommandDTO;
import com.matheustorres.eadhub.payment.services.PaymentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
@RequiredArgsConstructor
public class PaymentConsumer {

    private final PaymentService paymentService;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "${eadhub.broker.queue.paymentCommandQueue}", durable = "true"),
            exchange = @Exchange(value = "${eadhub.broker.exchange.paymentCommandExchange}", type = ExchangeTypes.TOPIC, ignoreDeclarationExceptions = "true"),
            key = "${eadhub.broker.key.paymentCommandKey}")
    )
    public void listenPaymentCommand(@Payload PaymentCommandDTO paymentCommandDto) {
        log.info("Received payment command for user: {}", paymentCommandDto.userId());
        paymentService.makePayment(paymentCommandDto);
    }
}
