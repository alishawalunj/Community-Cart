package com.nzefler.cart.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.math.BigDecimal;
import java.util.List;

@Component
public class OrderServiceClient {

    private final RestTemplate restTemplate;
    private final String orderServiceUrl;

    public OrderServiceClient(RestTemplate restTemplate,
                              @Value("${order-service.url}") String orderServiceUrl) {
        this.restTemplate = restTemplate;
        this.orderServiceUrl = orderServiceUrl;
    }

    public OrderResponseDTO createOrder(OrderRequestDTO request) {
        return restTemplate.postForObject(
                orderServiceUrl + "/api/orders", request, OrderResponseDTO.class);
    }

    public static class OrderRequestDTO {
        private Long userId;
        private Long cartId;
        private BigDecimal totalAmount;
        private List<OrderItemDTO> items;

        public OrderRequestDTO(Long userId, Long cartId, BigDecimal totalAmount, List<OrderItemDTO> items) {
            this.userId = userId;
            this.cartId = cartId;
            this.totalAmount = totalAmount;
            this.items = items;
        }
        public Long getUserId() { return userId; }
        public Long getCartId() { return cartId; }
        public BigDecimal getTotalAmount() { return totalAmount; }
        public List<OrderItemDTO> getItems() { return items; }
    }

    public static class OrderItemDTO {
        private Long productId;
        private String productName;
        private Integer quantity;
        private BigDecimal priceAtOrder;

        public OrderItemDTO(Long productId, String productName, Integer quantity, BigDecimal priceAtOrder) {
            this.productId = productId;
            this.productName = productName;
            this.quantity = quantity;
            this.priceAtOrder = priceAtOrder;
        }
        public Long getProductId() { return productId; }
        public String getProductName() { return productName; }
        public Integer getQuantity() { return quantity; }
        public BigDecimal getPriceAtOrder() { return priceAtOrder; }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class OrderResponseDTO {
        private Long orderId;
        public Long getOrderId() { return orderId; }
        public void setOrderId(Long orderId) { this.orderId = orderId; }
    }
}