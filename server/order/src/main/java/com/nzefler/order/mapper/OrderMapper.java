package com.nzefler.order.mapper;

import com.nzefler.order.dto.OrderResponseDTO;
import com.nzefler.order.model.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {

    public OrderResponseDTO toDTO(Order order){
        OrderResponseDTO dto = new OrderResponseDTO();
        dto.setOrderId(order.getOrderId());
        dto.setCartId(order.getCartId());
        dto.setUserId(order.getUserId());
        dto.setPaymentId(order.getPaymentId());
        dto.setTotalAmount(order.getTotalAmount());
        dto.setDate(order.getDate());
        return dto;
    }

    public Order toEntity(Order request){
        Order o = new Order();
        if(request.getOrderId() != null){
            o.setOrderId(request.getOrderId());
        }
        o.setCartId(request.getCartId());
        o.setUserId(request.getUserId());
        o.setPaymentId(request.getPaymentId());
        o.setTotalAmount(request.getTotalAmount());
        o.setDate(request.getDate());
        return o;
    }

}
