package com.nzefler.order.service;

import com.nzefler.order.dto.PaymentRequestDTO;
import com.nzefler.order.dto.PaymentResponseDTO;
import com.nzefler.order.dto.PaymentStatusUpdateDTO;
import com.nzefler.order.model.Payment;

import java.util.List;

public interface PaymentService {
    PaymentResponseDTO create(PaymentRequestDTO paymentRequestDTO);
    PaymentResponseDTO updateStatus(Long paymentId, PaymentStatusUpdateDTO dto);
    List<PaymentResponseDTO> findByUser(Long userId);
    PaymentResponseDTO findById(Long paymentId);
}
