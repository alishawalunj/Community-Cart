package com.nzefler.order.config;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE = "order.exchange";
    public static final String ORDER_PLACED_KEY = "order.placed";
    public static final String ORDER_STATUS_KEY = "order.status";
    public static final String PAYMENT_STATUS_KEY = "payment.status";

    @Bean
    public TopicExchange orderExchange(){
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }
}
