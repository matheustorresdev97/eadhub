package com.matheustorres.eadhub.notification.consumers;

import com.matheustorres.eadhub.notification.dtos.NotificationCommandDTO;
import com.matheustorres.eadhub.notification.mappers.NotificationMapper;
import com.matheustorres.eadhub.notification.services.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationConsumer {

    private final NotificationService notificationService;
    private final NotificationMapper notificationMapper;

    @RabbitListener(queues = "${eadhub.broker.queue.notificationCommand}")
    public void listen(@Payload NotificationCommandDTO notificationCommandDTO) {
        var notificationModel = notificationMapper.toEntity(notificationCommandDTO);
        notificationService.saveNotification(notificationModel);
    }
}
