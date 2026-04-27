package com.nzefler.product.mapper;

import com.nzefler.product.dto.ProductRequestDTO;
import com.nzefler.product.dto.ProductResponseDTO;
import com.nzefler.product.dto.ProductStatus;
import com.nzefler.product.model.Product;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ProductMapper {

    public Product toEntity(ProductRequestDTO request, Long sellerId){
        Product p = new Product();
        p.setUserId(sellerId);
        p.setCommunityId(request.getCommunityId());
        p.setName(request.getName());
        p.setDescription(request.getDescription());
        p.setImage(request.getImage());
        p.setTag(request.getTag());
        p.setColor(request.getColor());
        p.setSize(request.getSize());
        p.setPrice(request.getPrice());
        p.setStockCount(request.getStockCount());
        p.setStatus(ProductStatus.ACTIVE);
        p.setCreatedAt(LocalDateTime.now());
        return p;
    }

    public ProductResponseDTO toResponseDTO(Product p){
        ProductResponseDTO dto = new ProductResponseDTO();
        dto.setProductId(p.getProductId());
        dto.setUserId(p.getUserId());
        dto.setCommunityId(p.getCommunityId());
        dto.setName(p.getName());
        dto.setDescription(p.getDescription());
        dto.setImage(p.getImage());
        dto.setTag(p.getTag());
        dto.setColor(p.getColor());
        dto.setSize(p.getSize());
        dto.setPrice(p.getPrice());
        dto.setStockCount(p.getStockCount());
        dto.setStatus(p.getStatus());
        dto.setCreatedAt(p.getCreatedAt());
        return dto;
    }

}
