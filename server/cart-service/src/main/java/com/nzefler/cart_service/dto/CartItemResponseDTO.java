package com.nzefler.cart_service.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Objects;
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CartItemResponseDTO {
    private int ctId;
    private Integer productId;

    public int getCtId() {
        return ctId;
    }

    public void setCtId(int ctId) {
        this.ctId = ctId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CartItemResponseDTO that)) return false;
        return ctId == that.ctId && Objects.equals(productId, that.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ctId, productId);
    }
}
