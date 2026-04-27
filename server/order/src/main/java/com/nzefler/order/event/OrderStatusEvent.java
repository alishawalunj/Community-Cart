package com.nzefler.order.event;

import com.nzefler.order.enums.OrderStatus;

public class OrderStatusEvent {
    private Long orderId;
    private OrderStatus newStatus;
    private String userEmail;

    public OrderStatus getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(OrderStatus newStatus) {
        this.newStatus = newStatus;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
