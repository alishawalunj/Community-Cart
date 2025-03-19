package com.nzefler.product_service.service;

import com.nzefler.product_service.dto.ProductResponseDTO;
import com.nzefler.product_service.model.Product;

import java.util.List;

public interface ProductService {

    List<ProductResponseDTO> findAllProducts();
    ProductResponseDTO findProductById(Long productId);
    List<ProductResponseDTO> findProductsByCommunityId(Long communityId);
    List<ProductResponseDTO> findProductsByUserId(Long userId);
    String saveProduct(Product product);
    Product updateProduct(Product product);
    void deleteProduct(Long productId);

}
