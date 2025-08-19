package com.nzefler.order.repository;

import com.nzefler.order.model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface OrderRepository extends MongoRepository<Order, Long> {
    List<Order> findByUserId(Long userId);
}
