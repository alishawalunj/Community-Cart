package com.nzefler.product.model;

import com.nzefler.product.dto.ProductStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;


@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "product_seq")
    @SequenceGenerator(name = "product_seq", sequenceName = "product_sequence", allocationSize = 1, initialValue = 1301)
    private Long productId;
    private Long userId;
    private Long communityId;
    private String name;
    private String description;
    private String image;
    private String tag;
    private String color;
    private String size;
    private BigDecimal price;
    private Integer stockCount;
    @Enumerated(EnumType.STRING)
    private ProductStatus status;
    private LocalDateTime createdAt;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStockCount() {
        return stockCount;
    }

    public void setStockCount(Integer stockCount) {
        this.stockCount = stockCount;
    }

    public ProductStatus getStatus() {
        return status;
    }

    public void setStatus(ProductStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product product)) return false;
        return Objects.equals(productId, product.productId) && Objects.equals(userId, product.userId) && Objects.equals(communityId, product.communityId) && Objects.equals(name, product.name) && Objects.equals(description, product.description) && Objects.equals(image, product.image) && Objects.equals(tag, product.tag) && Objects.equals(color, product.color) && Objects.equals(size, product.size) && Objects.equals(price, product.price) && Objects.equals(stockCount, product.stockCount) && Objects.equals(status, product.status) && Objects.equals(createdAt, product.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, userId, communityId, name, description, image, tag, color, size, price, stockCount, status, createdAt);
    }
}
