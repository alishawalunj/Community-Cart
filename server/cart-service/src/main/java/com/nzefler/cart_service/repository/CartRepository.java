package com.nzefler.cart_service.repository;

import com.nzefler.cart_service.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CartRepository extends MongoRepository<Cart, Long> {
    List<Cart> findByUserId(Long userId);
}
