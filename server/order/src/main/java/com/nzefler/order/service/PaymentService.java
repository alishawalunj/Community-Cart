package com.nzefler.order.service;

import com.nzefler.order.dto.PaymentResponseDTO;
import com.nzefler.order.model.Payment;

import java.util.List;

public interface PaymentService {
    PaymentResponseDTO savePayment(Payment payment, String token);
    PaymentResponseDTO updatePayment(Payment payment, String token);
    List<PaymentResponseDTO> getAllPayments(String token);
    List<PaymentResponseDTO> getAllPaymentsByUserId(Long userId, String token);
    PaymentResponseDTO getPaymentById(Long paymentId, String token);
}
