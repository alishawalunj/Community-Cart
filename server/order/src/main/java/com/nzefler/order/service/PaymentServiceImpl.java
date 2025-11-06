package com.nzefler.order.service;

import com.nzefler.order.client.AuthServiceClient;
import com.nzefler.order.dto.PaymentResponseDTO;
import com.nzefler.order.mapper.PaymentMapper;
import com.nzefler.order.model.Payment;
import com.nzefler.order.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final AuthServiceClient client;
    private final PaymentMapper mapper;
    private final PaymentRepository paymentRepository;

    public PaymentServiceImpl(AuthServiceClient client, PaymentMapper mapper, PaymentRepository paymentRepository) {
        this.client = client;
        this.mapper = mapper;
        this.paymentRepository = paymentRepository;
    }

    private void validateToken(String token) {
        if (!client.isTokenValid(token)) {
            throw new RuntimeException("Unauthorized: Invalid token");
        }
    }

    @Override
    public PaymentResponseDTO savePayment(Payment payment, String token) {
        validateToken(token);
        Payment savedPayment = paymentRepository.save(mapper.toEntity(payment));
        return mapper.toDTO(savedPayment);
    }

    @Override
    public PaymentResponseDTO updatePayment(Payment payment, String token) {
        validateToken(token);
        Payment existingPayment = paymentRepository.findById(payment.getPaymentId())
                .orElseThrow(() -> new RuntimeException("Payment not found"));
        existingPayment.setAmount(payment.getAmount());
        existingPayment.setUserId(payment.getUserId());

        Payment updatedPayment = paymentRepository.save(existingPayment);
        return mapper.toDTO(updatedPayment);
    }

    @Override
    public List<PaymentResponseDTO> getAllPayments(String token) {
        validateToken(token);
        return paymentRepository.findAll()
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PaymentResponseDTO> getAllPaymentsByUserId(Long userId, String token) {
        validateToken(token);
        return paymentRepository.findByUserId(userId)
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PaymentResponseDTO getPaymentById(Long paymentId, String token) {
        validateToken(token);
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
        return mapper.toDTO(payment);
    }
}
