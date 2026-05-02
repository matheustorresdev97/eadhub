package com.matheustorres.eadhub.course.publishers;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.matheustorres.eadhub.course.dtos.NotificationCommandDTO;

@Component
@RequiredArgsConstructor
public class NotificationCommandPublisher {

    private final RabbitTemplate rabbitTemplate;

    @Value(value = "${eadhub.broker.queue.notificationCommand}")
    private String queueNotificationCommand;

    public void publishNotificationCommand(NotificationCommandDTO notificationCommandDTO) {
        rabbitTemplate.convertAndSend(queueNotificationCommand, notificationCommandDTO);
    }
}
