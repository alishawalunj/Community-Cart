package com.nzefler.order.controller;

import com.nzefler.order.dto.OrderResponseDTO;
import com.nzefler.order.model.Order;
import com.nzefler.order.service.OrderServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order-service")
public class OrderController {
    private final OrderServiceImpl orderService;

    public OrderController(OrderServiceImpl orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/getAllOrders")
    public List<OrderResponseDTO> getAllOrders(@RequestHeader("Authorization") String token){
        return orderService.getAllOrders(token);
    }

    @GetMapping("/getOrderById/{cartId}")
    public ResponseEntity<OrderResponseDTO> getOrderById(@PathVariable Long orderId, @RequestHeader("Authorization") String token) {
        OrderResponseDTO response = orderService.getOrderById(orderId, token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/getAllOrdersByUserId/{userId}")
    public List<OrderResponseDTO> getAllOrdersByUserId(@PathVariable Long userId, @RequestHeader("Authorization") String token){
        return orderService.getOrdersByUserId(userId, token);
    }

    @PostMapping("/createOrder")
    public ResponseEntity<OrderResponseDTO> createOrder(@RequestBody Order order, @RequestHeader("Authorization") String token){
        OrderResponseDTO response = orderService.saveOrder(order, token);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/deleteOrder/{orderId}")
    public ResponseEntity<Boolean> deleteOrder(@PathVariable Long orderId, @RequestHeader("Authorization") String token){
        orderService.deleteOrder(orderId, token);
        return ResponseEntity.noContent().build();
    }
}
