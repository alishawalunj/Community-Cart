package com.nzefler.order.service;

import com.nzefler.order.dto.OrderResponseDTO;
import com.nzefler.order.exception.EntityAlreadyExistsException;
import com.nzefler.order.exception.EntityNotFoundException;
import com.nzefler.order.exception.ErrorMessages;
import com.nzefler.order.mapper.OrderMapper;
import com.nzefler.order.model.Order;
import com.nzefler.order.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper mapper;

    public OrderServiceImpl(OrderRepository orderRepository, OrderMapper mapper) {
        this.orderRepository = orderRepository;
        this.mapper = mapper;
    }

    @Override
    public List<OrderResponseDTO> getAllOrders() {
        try{
            return orderRepository.findAll().stream().map(mapper::toDTO).collect(Collectors.toList());
        }catch(Exception e){
            throw new RuntimeException(ErrorMessages.ERROR_IN_PROCESSING);
        }
    }

    @Override
    public OrderResponseDTO getOrderById(Long orderId) {
        return orderRepository.findById(orderId).map(mapper::toDTO).orElseThrow(() -> new EntityNotFoundException(ErrorMessages.ORDER_DOES_NOT_EXIST));
    }

    @Override
    public List<OrderResponseDTO> getOrdersByUserId(Long userId) {
        try{
            return orderRepository.findById(userId).stream().map(mapper::toDTO).collect(Collectors.toList());
        }catch(Exception e){
            throw new RuntimeException(ErrorMessages.ERROR_IN_PROCESSING);
        }
    }

    @Override
    public OrderResponseDTO saveOrder(Order order) {
        try{
            if(order.getOrderId() != null) throw new EntityAlreadyExistsException(ErrorMessages.ORDER_ALREADY_EXIST);
            Order created = orderRepository.save(order);
            return mapper.toDTO(created);
        }catch (EntityAlreadyExistsException e){
            throw new EntityAlreadyExistsException(ErrorMessages.ORDER_ALREADY_EXIST);
        }catch (Exception ex){
            throw new RuntimeException(ErrorMessages.ERROR_IN_PROCESSING);
        }
    }

    @Override
    public void deleteOrder(Long orderId) {
        if (!orderRepository.existsById(orderId)) {
            throw new EntityNotFoundException(ErrorMessages.ORDER_DOES_NOT_EXIST);
        }
        orderRepository.deleteById(orderId);
    }
}
