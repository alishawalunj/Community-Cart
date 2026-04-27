package com.nzefler.product.service;

import com.nzefler.product.client.CommunityServiceClient;
import com.nzefler.product.dto.ProductRequestDTO;
import com.nzefler.product.dto.ProductResponseDTO;
import com.nzefler.product.dto.ProductStatus;
import com.nzefler.product.dto.StockUpdateDTO;
import com.nzefler.product.exception.ErrorConstants;
import com.nzefler.product.exception.MembershipException;
import com.nzefler.product.exception.UnAuthorizedException;
import com.nzefler.product.mapper.ProductMapper;
import com.nzefler.product.model.Product;
import com.nzefler.product.repository.ProductRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CommunityServiceClient communityServiceClient;

    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper, CommunityServiceClient communityServiceClient) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.communityServiceClient = communityServiceClient;
    }

    @Override
    public ProductResponseDTO create(Long sellerId, ProductRequestDTO request) {
        assertIsMember(sellerId, request.getCommunityId());
        Product product = productMapper.toEntity(request, sellerId);
        return productMapper.toResponseDTO(productRepository.save(product));
    }

    @Override
    public ProductResponseDTO findById(Long productId, Long requestingUserId) {
        Product product = findOrThrow(productId);
        assertIsMember(requestingUserId, product.getCommunityId());
        return productMapper.toResponseDTO(product);
    }

    @Override
    public ProductResponseDTO update(Long productId, Long requestingUserId, ProductRequestDTO request) {
        Product product = findOrThrow(productId);
        assertIsSeller(product, requestingUserId);
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setImage(request.getImage());
        product.setTag(request.getTag());
        product.setColor(request.getColor());
        product.setSize(request.getSize());
        product.setPrice(request.getPrice());
        product.setStockCount(request.getStockCount());
        return productMapper.toResponseDTO(productRepository.save(product));
    }

    @Override
    public void delete(Long productId, Long requestingUserId) {
        Product product = findOrThrow(productId);
        assertIsSeller(product, requestingUserId);
        product.setStatus(ProductStatus.REMOVED);
        productRepository.save(product);
    }

    @Override
    public List<ProductResponseDTO> findAllInUserCommunities(Long userId) {
        List<Long> communityIds = communityServiceClient.getUserCommunityIds(userId);
        return productRepository.findByCommunityIdIn(communityIds)
                .stream()
                .map(productMapper::toResponseDTO)
                .toList();
    }

    @Override
    public List<ProductResponseDTO> findByCommunity(Long communityId, Long requestingUserId) {
        assertIsMember(requestingUserId, communityId);
        return productRepository.findByCommunityId(communityId)
                .stream()
                .map(productMapper::toResponseDTO)
                .toList();
    }

    @Override
    public List<ProductResponseDTO> findByUser(Long userId) {
        return productRepository.findByUserId(userId)
                .stream()
                .map(productMapper::toResponseDTO)
                .toList();
    }

    @Override
    public void decrementStock(Long productId, StockUpdateDTO stockUpdate) {
        Product product = findOrThrow(productId);
        int updated = product.getStockCount() - stockUpdate.getQuantity();
        if(updated < 0) throw new RuntimeException(ErrorConstants.INSUFFICIENT_STOCK);
        product.setStockCount(updated);
        product.setStatus(updated == 0 ? ProductStatus.SOLD_OUT : ProductStatus.ACTIVE);
        productRepository.save(product);
    }

    private Product findOrThrow(Long id){
        return productRepository.findById(id).orElseThrow(() -> new UnAuthorizedException(ErrorConstants.PRODUCT_DOES_NOT_EXIST));
    }

    private void assertIsMember(Long userId, Long communityId){
        List<Long> ids = communityServiceClient.getUserCommunityIds(userId);
        if(ids == null || !ids.contains(communityId)){
            throw new MembershipException(ErrorConstants.NOT_A_MEMBER);
        }
    }

    private void assertIsSeller(Product product, Long requestingUserId){
        if(!product.getUserId().equals(requestingUserId)){
            throw new UnAuthorizedException(ErrorConstants.NOT_SELLER);
        }
    }
}
