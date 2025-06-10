package com.nzefler.cart.repository;

import com.nzefler.cart.model.Cart;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CartRepository extends MongoRepository<Cart, Long> {
    List<Cart> findByUserId(Long userId);
}
