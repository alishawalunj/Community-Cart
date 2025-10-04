package com.nzefler.order.service;

import com.nzefler.order.dto.OrderResponseDTO;
import com.nzefler.order.model.Order;

import java.util.List;

public interface OrderService {
    List<OrderResponseDTO> getAllOrders(String token);
    OrderResponseDTO getOrderById(Long orderId, String token);
    List<OrderResponseDTO> getOrdersByUserId(Long userId, String token);
    OrderResponseDTO saveOrder(Order order, String token);
    void deleteOrder(Long orderId, String token);
}
