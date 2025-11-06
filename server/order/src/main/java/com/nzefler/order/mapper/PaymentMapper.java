package com.nzefler.order.mapper;

import com.nzefler.order.dto.PaymentResponseDTO;
import com.nzefler.order.model.Payment;
import org.springframework.stereotype.Component;

import java.util.Date;
@Component
public class PaymentMapper {

    public Payment toEntity(Payment request){
        Payment p = new Payment();
        p.setUserId(request.getUserId());
        p.setAmount(request.getAmount());
        p.setDate(new Date());
        return p;
    }

    public PaymentResponseDTO toDTO(Payment p){
        PaymentResponseDTO dto = new PaymentResponseDTO();
        dto.setPaymentId(p.getPaymentId());
        dto.setUserId(p.getUserId());
        dto.setAmount(p.getAmount());
        return dto;
    }
}
