package com.nzefler.order.service;

import com.nzefler.order.client.UserServiceClient;
import com.nzefler.order.config.RabbitMQConfig;
import com.nzefler.order.dto.PaymentRequestDTO;
import com.nzefler.order.dto.PaymentResponseDTO;
import com.nzefler.order.dto.PaymentStatusUpdateDTO;
import com.nzefler.order.enums.PaymentStatus;
import com.nzefler.order.event.PaymentStatusEvent;
import com.nzefler.order.exception.EntityNotFoundException;
import com.nzefler.order.exception.ErrorConstants;
import com.nzefler.order.mapper.PaymentMapper;
import com.nzefler.order.model.Payment;
import com.nzefler.order.repository.PaymentRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    private final RabbitTemplate rabbitTemplate;
    private final UserServiceClient userServiceClient;

    public PaymentServiceImpl(PaymentMapper paymentMapper, PaymentRepository paymentRepository, RabbitTemplate rabbitTemplate, UserServiceClient userServiceClient) {
        this.paymentMapper = paymentMapper;
        this.paymentRepository = paymentRepository;
        this.rabbitTemplate = rabbitTemplate;
        this.userServiceClient = userServiceClient;
    }

    @Override
    public PaymentResponseDTO create(PaymentRequestDTO paymentRequestDTO) {
        Payment payment = new Payment();
        payment.setUserId(paymentRequestDTO.getUserId());
        payment.setOrderId(paymentRequestDTO.getOrderId());
        payment.setAmount(paymentRequestDTO.getAmount());
        payment.setMethod(paymentRequestDTO.getMethod());
        payment.setStatus(PaymentStatus.PENDING);
        payment.setGatewayRef(paymentRequestDTO.getGatewayRef());
        payment.setCreatedAt(LocalDateTime.now());
        return paymentMapper.toResponseDTO(paymentRepository.save(payment));
    }

    @Override
    public PaymentResponseDTO updateStatus(Long paymentId, PaymentStatusUpdateDTO dto) {
        Payment payment = findOrThrow(paymentId);
        payment.setStatus(dto.getStatus());
        Payment saved = paymentRepository.save(payment);
        String email = userServiceClient.getUserEmail(saved.getUserId());
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.PAYMENT_STATUS_KEY, new PaymentStatusEvent(saved.getPaymentId(), saved.getOrderId(), saved.getStatus(), email));
        return paymentMapper.toResponseDTO(saved);
    }

    @Override
    public List<PaymentResponseDTO> findByUser(Long userId) {
        return paymentRepository.findByUserId(userId).stream().map(paymentMapper::toResponseDTO).toList();
    }

    @Override
    public PaymentResponseDTO findById(Long paymentId) {
        return paymentMapper.toResponseDTO(findOrThrow(paymentId));
    }

    private Payment findOrThrow(Long id) {
        return paymentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(ErrorConstants.PAYMENT_NOT_FOUND));
    }
}
