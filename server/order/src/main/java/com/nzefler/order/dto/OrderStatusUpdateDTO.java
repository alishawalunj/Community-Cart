package com.nzefler.order.dto;

import com.nzefler.order.enums.OrderStatus;

public class OrderStatusUpdateDTO {
    private OrderStatus status;

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}
