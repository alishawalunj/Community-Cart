package com.nzefler.cart.dto;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class StockUpdateDTO {
    private int quantity;

    public StockUpdateDTO(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
