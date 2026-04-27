package com.nzefler.order.service;


import com.nzefler.order.client.UserServiceClient;
import com.nzefler.order.config.RabbitMQConfig;
import com.nzefler.order.dto.OrderRequestDTO;
import com.nzefler.order.dto.OrderResponseDTO;
import com.nzefler.order.dto.OrderStatusUpdateDTO;
import com.nzefler.order.enums.OrderStatus;
import com.nzefler.order.event.OrderStatusEvent;
import com.nzefler.order.exception.EntityNotFoundException;
import com.nzefler.order.mapper.OrderMapper;
import com.nzefler.order.model.Order;
import com.nzefler.order.model.OrderItem;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import com.nzefler.order.repository.OrderRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import com.nzefler.order.event.OrderPlacedEvent;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final RabbitTemplate rabbitTemplate;
    private final UserServiceClient userServiceClient;

    public OrderServiceImpl(OrderMapper orderMapper, OrderRepository orderRepository, RabbitTemplate rabbitTemplate, UserServiceClient userServiceClient) {
        this.orderMapper = orderMapper;
        this.orderRepository = orderRepository;
        this.rabbitTemplate = rabbitTemplate;
        this.userServiceClient = userServiceClient;
    }

    @Override
    public List<OrderResponseDTO> findAll() {
        return orderRepository.findAll().stream().map(orderMapper::toResponseDTO).toList();
    }

    @Override
    public OrderResponseDTO findById(Long orderId) {
        return orderMapper.toResponseDTO(findOrThrow(orderId));
    }

    @Override
    public List<OrderResponseDTO> findByUser(Long userId) {
        return orderRepository.findByUserId(userId).stream().map(orderMapper::toResponseDTO).toList();
    }

    @Override
    public OrderResponseDTO create(OrderRequestDTO orderRequestDTO) {
        Order order = new Order();
        order.setUserId(order.getUserId());
        order.setCartId(order.getCartId());
        order.setTotalAmount(orderRequestDTO.getTotalAmount());
        order.setStatus(orderRequestDTO.getStatus());
        order.setStatus(OrderStatus.PENDING);
        order.setCreatedAt(LocalDateTime.now());
        List<OrderItem> items = orderRequestDTO.getOrderItems().stream().map(i -> {
            OrderItem item = new OrderItem();
            item.setOrder(order);
            item.setProductId(i.getProductId());
            item.setProductName(i.getProductName());
            item.setQuantity(i.getQuantity());
            item.setPriceAtOrder(i.getPriceAtOrder());
            return item;
        }).collect(Collectors.toList());
        order.setOrderItems(items);
        Order saved = orderRepository.save(order);
        String email = userServiceClient.getUserEmail(saved.getUserId());
        List<OrderPlacedEvent.OrderItemDTO> eventItems = saved.getOrderItems().stream()
                .map(i -> new OrderPlacedEvent.OrderItemDTO(
                        i.getProductId(), i.getProductName(), i.getQuantity(), i.getPriceAtOrder()))
                .toList();

            rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.ORDER_PLACED_KEY, new OrderPlacedEvent(saved.getOrderId(), saved.getUserId(), email, saved.getTotalAmount(), eventItems));
        return orderMapper.toResponseDTO(saved);
    }

    @Override
    public OrderResponseDTO updateStatus(Long orderId, OrderStatusUpdateDTO dto) {
        Order order = findOrThrow(orderId);
        order.setStatus(dto.getStatus());
        Order saved = orderRepository.save(order);
        String email = userServiceClient.getUserEmail(saved.getUserId());
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.ORDER_STATUS_KEY,
                new OrderStatusEvent(saved.getOrderId(), saved.getStatus(), email));

        return orderMapper.toResponseDTO(saved);
    }

    @Override
    public void cancel(Long orderId) {
        Order order = findOrThrow(orderId);
        order.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);
    }

    private Order findOrThrow(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ErrorConstants.ORDER_NOT_FOUND));
    }
}
