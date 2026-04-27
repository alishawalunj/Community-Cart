package com.nzefler.cart.dto;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class CartRequestDTO {
    private Long userId;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<CartItemRequestDTO> cartItemRequestDTO;

    public List<CartItemRequestDTO> getCartItemRequestDTO() {
        return cartItemRequestDTO;
    }

    public void setCartItemRequestDTO(List<CartItemRequestDTO> cartItemRequestDTO) {
        this.cartItemRequestDTO = cartItemRequestDTO;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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
