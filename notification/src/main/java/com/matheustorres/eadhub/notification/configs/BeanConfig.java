package com.matheustorres.eadhub.notification.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.matheustorres.eadhub.notification.core.ports.out.NotificationRepositoryPort;
import com.matheustorres.eadhub.notification.core.usecases.NotificationUseCaseImpl;

@Configuration
public class BeanConfig {

    @Bean
    public NotificationUseCaseImpl notificationUseCaseImpl(NotificationRepositoryPort notificationRepositoryPort) {
        return new NotificationUseCaseImpl(notificationRepositoryPort);
    }
}
