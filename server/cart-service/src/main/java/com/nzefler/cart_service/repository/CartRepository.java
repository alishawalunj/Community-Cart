package com.nzefler.cart_service.repository;

import com.nzefler.cart_service.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
