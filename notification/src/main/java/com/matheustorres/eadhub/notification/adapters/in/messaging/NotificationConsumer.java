package com.matheustorres.eadhub.notification.adapters.in.messaging;

import com.matheustorres.eadhub.notification.adapters.in.messaging.dto.NotificationCommandDTO;
import com.matheustorres.eadhub.notification.core.ports.in.CreateNotificationUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationConsumer {

    private final CreateNotificationUseCase createNotificationUseCase;

    @RabbitListener(queues = "${eadhub.broker.queue.notificationCommand}")
    public void listen(@Payload NotificationCommandDTO notificationCommandDTO) {
        createNotificationUseCase.create(
                notificationCommandDTO.userId(),
                notificationCommandDTO.title(),
                notificationCommandDTO.message()
        );
    }
}
