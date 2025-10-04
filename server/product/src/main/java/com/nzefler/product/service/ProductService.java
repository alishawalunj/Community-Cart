package com.nzefler.product.service;

import com.nzefler.product.dto.ProductRequestDTO;
import com.nzefler.product.dto.ProductResponseDTO;
import com.nzefler.product.model.Product;

import java.util.List;

public interface ProductService {
    List<ProductResponseDTO> findAllProducts(String token);
    ProductResponseDTO findProductById(Long productId, String token);

    ProductResponseDTO saveProduct(ProductRequestDTO product, String token);
    ProductResponseDTO updateProduct(Product product, String token);
    Boolean deleteProduct(Long productId, String token);

    List<ProductResponseDTO> findProductsByCommunityId(Long communityId, String token);
    List<ProductResponseDTO> findProductsByUserId(Long userId, String token);

}
