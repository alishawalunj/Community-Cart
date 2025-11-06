package com.nzefler.order.controller;

import com.nzefler.order.dto.PaymentResponseDTO;
import com.nzefler.order.model.Payment;
import com.nzefler.order.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payment-service")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/createPayment")
    public ResponseEntity<PaymentResponseDTO> createPayment(
            @RequestBody Payment payment,
            @RequestHeader("Authorization") String token
    ) {
        PaymentResponseDTO response = paymentService.savePayment(payment, token);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/updatePayment")
    public ResponseEntity<PaymentResponseDTO> updatePayment(
            @RequestBody Payment payment,
            @RequestHeader("Authorization") String token
    ) {
        PaymentResponseDTO response = paymentService.updatePayment(payment, token);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getAllPayments")
    public ResponseEntity<List<PaymentResponseDTO>> getAllPayments(
            @RequestHeader("Authorization") String token
    ) {
        List<PaymentResponseDTO> payments = paymentService.getAllPayments(token);
        return ResponseEntity.ok(payments);
    }

    @GetMapping("/getPaymentById/{paymentId}")
    public ResponseEntity<PaymentResponseDTO> getPaymentById(
            @PathVariable Long paymentId,
            @RequestHeader("Authorization") String token
    ) {
        PaymentResponseDTO payment = paymentService.getPaymentById(paymentId, token);
        return ResponseEntity.ok(payment);
    }

    @GetMapping("/getPaymentsByUserId/user/{userId}")
    public ResponseEntity<List<PaymentResponseDTO>> getPaymentsByUserId(
            @PathVariable Long userId,
            @RequestHeader("Authorization") String token
    ) {
        List<PaymentResponseDTO> payments = paymentService.getAllPaymentsByUserId(userId, token);
        return ResponseEntity.ok(payments);
    }
}
