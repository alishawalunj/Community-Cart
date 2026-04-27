package com.nzefler.notification.config;

import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.naming.Binding;
import java.util.Queue;

@Configuration
public class RabbitMQConfig {
    public static final String EXCHANGE             = "order.exchange";
    public static final String ORDER_PLACED_KEY     = "order.placed";
    public static final String ORDER_STATUS_KEY     = "order.status";
    public static final String PAYMENT_STATUS_KEY   = "payment.status";

    public static final String ORDER_PLACED_QUEUE   = "notif.order.placed";
    public static final String ORDER_STATUS_QUEUE   = "notif.order.status";
    public static final String PAYMENT_STATUS_QUEUE = "notif.payment.status";

    @Bean
    public TopicExchange orderExchange() { return new TopicExchange(EXCHANGE); }

    @Bean public Queue orderPlacedQueue()   { return new Queue(ORDER_PLACED_QUEUE); }
    @Bean public Queue orderStatusQueue()   { return new Queue(ORDER_STATUS_QUEUE); }
    @Bean public Queue paymentStatusQueue() { return new Queue(PAYMENT_STATUS_QUEUE); }

    @Bean public Binding orderPlacedBinding(Queue orderPlacedQueue, TopicExchange orderExchange) {
        return BindingBuilder.bind(orderPlacedQueue).to(orderExchange).with(ORDER_PLACED_KEY);
    }
    @Bean public Binding orderStatusBinding(Queue orderStatusQueue, TopicExchange orderExchange) {
        return BindingBuilder.bind(orderStatusQueue).to(orderExchange).with(ORDER_STATUS_KEY);
    }
    @Bean public Binding paymentStatusBinding(Queue paymentStatusQueue, TopicExchange orderExchange) {
        return BindingBuilder.bind(paymentStatusQueue).to(orderExchange).with(PAYMENT_STATUS_KEY);
    }

    @Bean public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
