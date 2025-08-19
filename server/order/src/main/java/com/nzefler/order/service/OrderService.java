package com.nzefler.order.service;

import com.nzefler.order.dto.OrderResponseDTO;
import com.nzefler.order.model.Order;

import java.util.List;

public interface OrderService {
    List<OrderResponseDTO> getAllOrders();
    OrderResponseDTO getOrderById(Long orderId);
    List<OrderResponseDTO> getOrdersByUserId(Long userId);
    OrderResponseDTO saveOrder(Order order);
    void deleteOrder(Long orderId);
}
