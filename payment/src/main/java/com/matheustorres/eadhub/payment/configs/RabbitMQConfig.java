package com.matheustorres.eadhub.payment.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import lombok.RequiredArgsConstructor;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RabbitMQConfig {

    private final CachingConnectionFactory cachingConnectionFactory;

    @Value(value = "${eadhub.broker.queue.userEvent}")
    private String queueUserEvent;

    @Value(value = "${eadhub.broker.exchange.userEvent}")
    private String exchangeUserEvent;

    @Value(value = "${eadhub.broker.exchange.paymentEvent}")
    private String exchangePaymentEvent;

    @Bean
    public Queue queueUserEvent() {
        return new Queue(queueUserEvent, true);
    }

    @Bean
    public FanoutExchange fanoutUserEvent() {
        return new FanoutExchange(exchangeUserEvent);
    }

    @Bean
    public FanoutExchange fanoutPaymentEvent() {
        return new FanoutExchange(exchangePaymentEvent);
    }

    @Bean
    public Binding bindingUserEvent() {
        return BindingBuilder.bind(queueUserEvent()).to(fanoutUserEvent());
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
