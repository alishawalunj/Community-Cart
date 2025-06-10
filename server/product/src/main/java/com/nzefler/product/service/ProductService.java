package com.nzefler.product.service;

import com.nzefler.product.dto.ProductRequestDTO;
import com.nzefler.product.dto.ProductResponseDTO;
import com.nzefler.product.model.Product;

import java.util.List;

public interface ProductService {
    List<ProductResponseDTO> findAllProducts();
    ProductResponseDTO findProductById(Long productId);
    List<ProductResponseDTO> findProductsByCommunityId(Long communityId);
    List<ProductResponseDTO> findProductsByUserId(Long userId);
    List<ProductResponseDTO> findProductsByUserCommunities(Long userId);
    ProductResponseDTO saveProduct(ProductRequestDTO product);
    ProductResponseDTO updateProduct(Product product);
    Boolean deleteProduct(Long productId);
}
