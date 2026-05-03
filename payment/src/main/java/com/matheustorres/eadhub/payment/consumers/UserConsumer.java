package com.matheustorres.eadhub.payment.consumers;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.matheustorres.eadhub.payment.domain.enums.ActionType;
import com.matheustorres.eadhub.payment.domain.enums.PaymentStatus;
import com.matheustorres.eadhub.payment.dtos.UserEventDTO;
import com.matheustorres.eadhub.payment.mappers.UserMapper;
import com.matheustorres.eadhub.payment.services.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
@RequiredArgsConstructor
public class UserConsumer {

    private final UserService userService;
    private final UserMapper userMapper;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "${eadhub.broker.queue.userEventQueue}", durable = "true"),
            exchange = @Exchange(value = "${eadhub.broker.exchange.userEventExchange}", type = ExchangeTypes.FANOUT, ignoreDeclarationExceptions = "true"))
    )
    public void listenUserEvent(@Payload UserEventDTO userEventDto) {
        var user = userMapper.toEntity(userEventDto);

        switch (ActionType.valueOf(userEventDto.actionType())) {
            case CREATE -> {
                user.setPaymentStatus(PaymentStatus.NOTSTARTED);
                userService.save(user);
                log.info("User {} created in payment service", user.getUserId());
            }
            case UPDATE -> {
                userService.save(user);
                log.info("User {} updated in payment service", user.getUserId());
            }
            case DELETE -> {
                userService.delete(userEventDto.userId());
                log.info("User {} deleted from payment service", userEventDto.userId());
            }
        }
    }
}
