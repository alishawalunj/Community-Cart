package com.nzefler.cart.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@Component
public class ProductServiceClient {

    private final RestTemplate restTemplate;
    private final String productServiceUrl;

    public ProductServiceClient(RestTemplate restTemplate,
                                @Value("${product-service.url}") String productServiceUrl) {
        this.restTemplate = restTemplate;
        this.productServiceUrl = productServiceUrl;
    }

    public ProductDTO getProduct(Long productId) {
        return restTemplate.getForObject(
                productServiceUrl + "/api/products/" + productId, ProductDTO.class);
    }

    public void decrementStock(Long productId, int quantity) {
        StockUpdateDTO dto = new StockUpdateDTO(quantity);
        restTemplate.patchForObject(
                productServiceUrl + "/api/products/" + productId + "/stock", dto, Void.class);
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ProductDTO {
        private Long productId;
        private Long communityId;
        private String name;
        private BigDecimal price;
        private Integer stockCount;

        public Long getProductId() { return productId; }
        public void setProductId(Long productId) { this.productId = productId; }
        public Long getCommunityId() { return communityId; }
        public void setCommunityId(Long communityId) { this.communityId = communityId; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public BigDecimal getPrice() { return price; }
        public void setPrice(BigDecimal price) { this.price = price; }
        public Integer getStockCount() { return stockCount; }
        public void setStockCount(Integer stockCount) { this.stockCount = stockCount; }
    }

    public static class StockUpdateDTO {
        private Integer quantity;
        public StockUpdateDTO(Integer quantity) { this.quantity = quantity; }
        public Integer getQuantity() { return quantity; }
        public void setQuantity(Integer quantity) { this.quantity = quantity; }
    }
}