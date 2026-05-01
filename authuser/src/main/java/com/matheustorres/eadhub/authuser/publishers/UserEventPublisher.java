package com.matheustorres.eadhub.authuser.publishers;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.matheustorres.eadhub.authuser.dtos.UserEventDTO;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    @Value(value = "${eadhub.broker.exchange.userEvent}")
    private String exchangeUserEvent;

    public void publishUserEvent(UserEventDTO userEventDto) {
        rabbitTemplate.convertAndSend(exchangeUserEvent, "", userEventDto);
    }
}
