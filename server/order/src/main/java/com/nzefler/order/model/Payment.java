package com.nzefler.order.model;

import com.nzefler.order.enums.PaymentMethod;
import com.nzefler.order.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "payment_seq")
    @SequenceGenerator(name = "payment_seq", sequenceName = "payment_sequence", allocationSize = 1, initialValue = 1501)
    private Long paymentId;
    private Long userId;
    private Long orderId;
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private PaymentMethod method;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    private String gatewayRef;
    private LocalDateTime createdAt;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getGatewayRef() {
        return gatewayRef;
    }

    public void setGatewayRef(String gatewayRef) {
        this.gatewayRef = gatewayRef;
    }

    public PaymentMethod getMethod() {
        return method;
    }

    public void setMethod(PaymentMethod method) {
        this.method = method;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Payment payment)) return false;
        return Objects.equals(paymentId, payment.paymentId) && Objects.equals(userId, payment.userId) && Objects.equals(orderId, payment.orderId) && Objects.equals(amount, payment.amount) && method == payment.method && status == payment.status && Objects.equals(gatewayRef, payment.gatewayRef) && Objects.equals(createdAt, payment.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(paymentId, userId, orderId, amount, method, status, gatewayRef, createdAt);
    }
}
