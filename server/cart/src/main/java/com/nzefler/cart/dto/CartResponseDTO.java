package com.nzefler.cart.dto;

import com.nzefler.cart.enums.CartStatus;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CartResponseDTO {
    private Long cartId;
    private Long userId;
    private CartStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<CartItemResponseDTO> cartItems;

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public List<CartItemResponseDTO> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItemResponseDTO> cartItems) {
        this.cartItems = cartItems;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public CartStatus getStatus() {
        return status;
    }

    public void setStatus(CartStatus status) {
        this.status = status;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
