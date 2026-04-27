package com.nzefler.order.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "order_items")
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderItemId;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
    private Long productId;
    private String productName;
    private Integer quantity;
    private BigDecimal priceAtOrder;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Long getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(Long orderItemId) {
        this.orderItemId = orderItemId;
    }

    public BigDecimal getPriceAtOrder() {
        return priceAtOrder;
    }

    public void setPriceAtOrder(BigDecimal priceAtOrder) {
        this.priceAtOrder = priceAtOrder;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderItem orderItem)) return false;
        return Objects.equals(orderItemId, orderItem.orderItemId) && Objects.equals(order, orderItem.order) && Objects.equals(productId, orderItem.productId) && Objects.equals(productName, orderItem.productName) && Objects.equals(quantity, orderItem.quantity) && Objects.equals(priceAtOrder, orderItem.priceAtOrder);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderItemId, order, productId, productName, quantity, priceAtOrder);
    }
}
