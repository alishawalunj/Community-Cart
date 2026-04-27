package com.nzefler.order.dto;

import com.nzefler.order.enums.PaymentMethod;
import com.nzefler.order.enums.PaymentStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PaymentRequestDTO {
    private Long userId;
    private Long orderId;
    private BigDecimal amount;
    private PaymentMethod method;
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
}
