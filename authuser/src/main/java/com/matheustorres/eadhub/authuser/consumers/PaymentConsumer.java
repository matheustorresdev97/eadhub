package com.matheustorres.eadhub.authuser.consumers;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.matheustorres.eadhub.authuser.dtos.PaymentEventDTO;
import com.matheustorres.eadhub.authuser.services.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
@RequiredArgsConstructor
public class PaymentConsumer {

    private final UserService userService;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "${eadhub.broker.queue.paymentEventQueue}", durable = "true"),
            exchange = @Exchange(value = "${eadhub.broker.exchange.paymentEventExchange}", type = ExchangeTypes.FANOUT, ignoreDeclarationExceptions = "true"))
    )
    public void listenPaymentEvent(@Payload PaymentEventDTO paymentEventDto) {
        log.info("Received payment event for user: {}", paymentEventDto.userId());
        userService.updateAfterPayment(paymentEventDto);
    }
}
