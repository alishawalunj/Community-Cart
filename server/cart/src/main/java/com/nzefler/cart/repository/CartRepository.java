package com.nzefler.cart.repository;

import com.nzefler.cart.enums.CartStatus;
import com.nzefler.cart.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUserIdAndStatus(Long userId, CartStatus status);
    List<Cart> findByUserId(Long userId);
}
