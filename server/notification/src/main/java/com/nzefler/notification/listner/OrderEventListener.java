package com.nzefler.notification.listner;

import com.nzefler.notification.config.RabbitMQConfig;
import com.nzefler.notification.event.OrderPlacedEvent;
import com.nzefler.notification.event.OrderStatusEvent;
import com.nzefler.notification.service.EmailService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static java.util.concurrent.Future.State.CANCELLED;

@Component
public class OrderEventListener {
    private final EmailService emailService;

    public OrderEventListener(EmailService emailService) {
        this.emailService = emailService;
    }

    @RabbitListener(queues = RabbitMQConfig.ORDER_PLACED_QUEUE)
    public void handleOrderPlaced(OrderPlacedEvent event) {
        String body = "Hi, your order #" + event.getOrderId() +
                " has been placed. Total: $" + event.getTotalAmount();
        emailService.send(event.getUserEmail(), "Order Confirmation", body);
    }

    @RabbitListener(queues = RabbitMQConfig.ORDER_STATUS_QUEUE)
    public void handleOrderStatus(OrderStatusEvent event) {
        String subject = switch (event.getNewStatus()) {
            case SHIPPED   -> "Your order has been shipped!";
            case DELIVERED -> "Your order has been delivered!";
            case CANCELLED -> "Your order has been cancelled.";
            default        -> "Order status update";
        };
        String body = "Order #" + event.getOrderId() + " status: " + event.getNewStatus();
        emailService.send(event.getUserEmail(), subject, body);
    }
}
