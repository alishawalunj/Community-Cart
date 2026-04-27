package com.nzefler.order.event;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class OrderPlacedEvent {

    private Long orderId;
    private Long userId;
    private String userEmail;
    private BigDecimal totalAmount;
    private List<OrderItemDTO> items;

    public List<OrderItemDTO> getItems() {
        return items;
    }

    public void setItems(List<OrderItemDTO> items) {
        this.items = items;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public static class OrderItemDTO {
        private Long productId;
        private String productName;
        private Integer quantity;
        private BigDecimal priceAtOrder;

        public OrderItemDTO() {}
        public OrderItemDTO(Long productId, String productName, Integer quantity, BigDecimal priceAtOrder) {
            this.productId = productId; this.productName = productName;
            this.quantity = quantity; this.priceAtOrder = priceAtOrder;
        }
        public Long getProductId() { return productId; }
        public String getProductName() { return productName; }
        public Integer getQuantity() { return quantity; }
        public BigDecimal getPriceAtOrder() { return priceAtOrder; }
    }
}
