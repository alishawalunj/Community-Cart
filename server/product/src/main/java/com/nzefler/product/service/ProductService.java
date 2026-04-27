package com.nzefler.product.service;

import com.nzefler.product.dto.ProductRequestDTO;
import com.nzefler.product.dto.ProductResponseDTO;
import com.nzefler.product.dto.StockUpdateDTO;
import java.util.List;

public interface ProductService {
    ProductResponseDTO create(Long sellerId, ProductRequestDTO request);
    ProductResponseDTO findById(Long productId, Long requestingUserId);
    ProductResponseDTO update(Long productId, Long requestingUserId, ProductRequestDTO request);
    void delete(Long productId, Long requestingUserId);
    List<ProductResponseDTO> findAllInUserCommunities(Long userId);
    List<ProductResponseDTO> findByCommunity(Long communityId, Long requestingUserId);
    List<ProductResponseDTO> findByUser(Long userId);
    void decrementStock(Long productId, StockUpdateDTO stockUpdate);
}
