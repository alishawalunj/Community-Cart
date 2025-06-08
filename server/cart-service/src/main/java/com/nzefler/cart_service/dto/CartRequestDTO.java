package com.nzefler.cart_service.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
import java.util.Objects;
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CartRequestDTO {
    private Long userId;
    private List<CartItemRequestDTO> items;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<CartItemRequestDTO> getItems() {
        return items;
    }

    public void setItems(List<CartItemRequestDTO> items) {
        this.items = items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CartRequestDTO that)) return false;
        return Objects.equals(userId, that.userId) && Objects.equals(items, that.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, items);
    }
}