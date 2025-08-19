package com.nzefler.cart.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CartResponseDTO {
    private Long cartId;
    private Long userId;
    private Date createdAt;
    private Date updatedAt;
    private List<CartItemResponseDTO> cartItems;

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<CartItemResponseDTO> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItemResponseDTO> cartItems) {
        this.cartItems = cartItems;
    }
}
