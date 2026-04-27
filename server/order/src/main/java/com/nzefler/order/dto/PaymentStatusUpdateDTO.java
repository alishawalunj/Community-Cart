package com.nzefler.order.dto;

import com.nzefler.order.enums.PaymentStatus;

public class PaymentStatusUpdateDTO {

    public PaymentStatus status;

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }
}
