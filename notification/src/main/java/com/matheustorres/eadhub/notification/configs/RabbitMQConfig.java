package com.matheustorres.eadhub.notification.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RabbitMQConfig {

    private final CachingConnectionFactory cachingConnectionFactory;

    @Value(value = "${eadhub.broker.queue.notificationCommand}")
    private String queueNotificationCommand;

    @Bean
    public Queue queueNotificationCommand() {
        return new Queue(queueNotificationCommand, true);
    }

    @Value(value = "${eadhub.broker.exchange.userEvent}")
    private String exchangeUserEvent;

    @Bean
    public FanoutExchange fanoutUserEvent() {
        return new FanoutExchange(exchangeUserEvent);
    }

    @Bean
    public Binding bindingUserEvent() {
        return BindingBuilder.bind(queueNotificationCommand()).to(fanoutUserEvent());
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate template = new RabbitTemplate(cachingConnectionFactory);
        template.setMessageConverter(messageConverter());
        return template;
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return new Jackson2JsonMessageConverter(objectMapper);
    }
}
