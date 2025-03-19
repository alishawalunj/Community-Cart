package com.nzefler.product_service.mapper;

import com.nzefler.product_service.dto.ProductResponseDTO;
import com.nzefler.product_service.model.Product;

public class ProductMapper {

    public ProductResponseDTO toDto(Product product){
        ProductResponseDTO response = new ProductResponseDTO();
        response.setProductId(product.getProductId());
        response.setCommunityId(product.getCommunityId());
        response.setUserId(product.getUserId());
        response.setName(product.getName());
        response.setDescription(product.getDescription());
        response.setType(product.getType());
        response.setTag(product.getTag());
        response.setCategory(product.getCategory());
        response.setColor(product.getColor());
        response.setSize(product.getSize());
        response.setPrice(product.getPrice());
        return response;
    }

    public Product toEntity(Product existingProduct, ProductResponseDTO productDto){
        existingProduct.setProductId(productDto.getProductId());
        existingProduct.setCommunityId(productDto.getCommunityId());
        existingProduct.setUserId(productDto.getUserId());
        existingProduct.setName(productDto.getName());
        existingProduct.setDescription(productDto.getDescription());
        existingProduct.setType(productDto.getType());
        existingProduct.setTag(productDto.getTag());
        existingProduct.setCategory(productDto.getCategory());
        existingProduct.setColor(productDto.getColor());
        existingProduct.setSize(productDto.getSize());
        existingProduct.setPrice(productDto.getPrice());
        return existingProduct;
    }

    public Product updateExistingProduct(Product existingProduct, Product newProduct){
        existingProduct.setProductId(newProduct.getProductId());
        existingProduct.setCommunityId(newProduct.getCommunityId());
        existingProduct.setUserId(newProduct.getUserId());
        existingProduct.setName(newProduct.getName());
        existingProduct.setDescription(newProduct.getDescription());
        existingProduct.setType(newProduct.getType());
        existingProduct.setTag(newProduct.getTag());
        existingProduct.setCategory(newProduct.getCategory());
        existingProduct.setColor(newProduct.getColor());
        existingProduct.setSize(newProduct.getSize());
        existingProduct.setPrice(newProduct.getPrice());
        return existingProduct;
    }
}
