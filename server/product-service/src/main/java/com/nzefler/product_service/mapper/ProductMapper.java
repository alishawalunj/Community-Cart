package com.nzefler.product_service.mapper;

import com.nzefler.product_service.dto.ProductRequestDTO;
import com.nzefler.product_service.dto.ProductResponseDTO;
import com.nzefler.product_service.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public ProductResponseDTO toDto(Product product){
        ProductResponseDTO response = new ProductResponseDTO();
        response.setProductId(product.getProductId());
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

    public Product updateExistingProduct(Product existingProduct, Product newProduct){
        existingProduct.setProductId(newProduct.getProductId());
        existingProduct.setCommunityId(newProduct.getCommunityId());
        existingProduct.setUserId(newProduct.getUserId());
        existingProduct.setName(newProduct.getName());
        existingProduct.setDescription(newProduct.getDescription());
        existingProduct.setTag(newProduct.getTag());
        existingProduct.setColor(newProduct.getColor());
        existingProduct.setSize(newProduct.getSize());
        existingProduct.setPrice(newProduct.getPrice());
        existingProduct.setCount(newProduct.getCount());
        return existingProduct;
    }
}
