package com.nzefler.order.mapper;

import com.nzefler.order.dto.OrderItemResponseDTO;
import com.nzefler.order.dto.OrderResponseDTO;
import com.nzefler.order.model.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@Component
public class OrderMapper {
    public OrderResponseDTO toDTO(Order order){
        OrderResponseDTO response = new OrderResponseDTO();
        response.setOrderId(order.getOrderId());
        response.setUserId(order.getUserId());
        response.setTotalAmount(order.getTotalAmount());
        response.setPaymentId(order.getPaymentId());
        response.setCreatedAt(order.getCreatedAt());
        response.setUpdateAt(order.getUpdateAt());
        List<OrderItemResponseDTO> itemsList = new ArrayList<>();
        if(order.getOrderItems() != null && !order.getOrderItems().isEmpty()){
            itemsList = order.getOrderItems().stream().map(item ->{
                OrderItemResponseDTO itemResponseDTO = new OrderItemResponseDTO();
                itemResponseDTO.setOrderItemId(item.getOrderItemId());
                itemResponseDTO.setOrderId(item.getOrderId());
                itemResponseDTO.setProductId(item.getProductId());
                itemResponseDTO.setPrice(item.getPrice());
                itemResponseDTO.setQuantity(item.getQuantity());
                return itemResponseDTO;
            }).collect(Collectors.toList());
        }
        response.setOrderItems(itemsList);
        return response;
    }
}
