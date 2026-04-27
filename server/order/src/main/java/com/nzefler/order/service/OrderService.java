package com.nzefler.order.service;

import com.nzefler.order.dto.OrderRequestDTO;
import com.nzefler.order.dto.OrderResponseDTO;
import com.nzefler.order.dto.OrderStatusUpdateDTO;
import java.util.List;

public interface OrderService {
    List<OrderResponseDTO> findAll();
    OrderResponseDTO findById(Long orderId);
    List<OrderResponseDTO> findByUser(Long userId);
    OrderResponseDTO create(OrderRequestDTO orderRequestDTO);
    OrderResponseDTO updateStatus(Long orderId, OrderStatusUpdateDTO dto);
    void cancel(Long orderId);
}
