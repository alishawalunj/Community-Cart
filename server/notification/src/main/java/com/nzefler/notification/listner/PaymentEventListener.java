package com.nzefler.notification.listner;

import com.nzefler.notification.config.RabbitMQConfig;
import com.nzefler.notification.service.EmailService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class PaymentEventListener {
    private final EmailService emailService;

    public PaymentEventListener(EmailService emailService) {
        this.emailService = emailService;
    }

    @RabbitListener(queues = RabbitMQConfig.PAYMENT_STATUS_QUEUE)
    public void handlePaymentStatus(PaymentStatusEvent event) {
        String subject;
        String body;

        switch (event.getStatus()) {
            case SUCCESS -> {
                subject = "Payment successful";
                body = "Payment #" + event.getPaymentId() + " for order #" +
                        event.getOrderId() + " was successful.";
            }
            case FAILED -> {
                subject = "Payment failed";
                body = "Payment #" + event.getPaymentId() + " failed. Please retry.";
            }
            case REFUNDED -> {
                subject = "Payment refunded";
                body = "Payment #" + event.getPaymentId() + " has been refunded.";
            }
            default -> {
                subject = "Payment update";
                body = "Payment status: " + event.getStatus();
            }
        }
        emailService.send(event.getUserEmail(), subject, body);
    }
}
