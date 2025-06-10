package com.nzefler.product.mapper;

import com.nzefler.product.dto.ProductRequestDTO;
import com.nzefler.product.dto.ProductResponseDTO;
import com.nzefler.product.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public ProductResponseDTO toDto(Product product){
        ProductResponseDTO response = new ProductResponseDTO();
        response.setProductId(product.getProductId());
        response.setImage(product.getImage());
        response.setCommunityId(product.getCommunityId());
        response.setUserId(product.getUserId());
        response.setName(product.getName());
        response.setDescription(product.getDescription());
        response.setTag(product.getTag());
        response.setColor(product.getColor());
        response.setSize(product.getSize());
        response.setPrice(product.getPrice());
        response.setCount(product.getCount());
        return response;
    }

    public Product toEntity(ProductRequestDTO requestDTO){
        Product newProduct = new Product();
        newProduct.setImage(requestDTO.getImage());
        newProduct.setCommunityId(requestDTO.getCommunityId());
        newProduct.setUserId(requestDTO.getUserId());
        newProduct.setName(requestDTO.getName());
        newProduct.setDescription(requestDTO.getDescription());
        newProduct.setTag(requestDTO.getTag());
        newProduct.setColor(requestDTO.getColor());
        newProduct.setSize(requestDTO.getSize());
        newProduct.setPrice(requestDTO.getPrice());
        newProduct.setCount(requestDTO.getCount());
        return newProduct;
    }

}
