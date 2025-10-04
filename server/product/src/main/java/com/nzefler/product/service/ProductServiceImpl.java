package com.nzefler.product.service;


import com.nzefler.product.client.AuthServiceClient;
import com.nzefler.product.dto.ProductRequestDTO;
import com.nzefler.product.dto.ProductResponseDTO;
import com.nzefler.product.exception.EntityNotFoundException;
import com.nzefler.product.exception.ErrorMessages;
import com.nzefler.product.mapper.ProductMapper;
import com.nzefler.product.model.Product;
import com.nzefler.product.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;
    private final ProductMapper mapper;
    private final AuthServiceClient client;

    public ProductServiceImpl(ProductRepository productRepository, ProductMapper mapper, AuthServiceClient client) {
        this.productRepository = productRepository;
        this.mapper = mapper;
        this.client = client;
    }

    private void validateToken(String token){
        if(!client.isTokenValid(token.substring(7))){
            throw new RuntimeException("Unauthorized : invalid token");
        }
    }


    @Override
    public List<ProductResponseDTO> findAllProducts(String token) {
        try{
            validateToken(token);
            return productRepository.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
        }catch(Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public ProductResponseDTO findProductById(Long productId, String token) {
        validateToken(token);
        return productRepository.findById(productId).map(mapper::toDto).orElseThrow(() -> new EntityNotFoundException(ErrorMessages.PRODUCT_DOES_NOT_EXIST));
    }

    @Override
    public List<ProductResponseDTO> findProductsByCommunityId(Long communityId, String token) {
        try{
            validateToken(token);
            return productRepository.findByCommunityId(communityId).stream().map(mapper::toDto).collect(Collectors.toList());
        }catch(Exception e){
            throw new RuntimeException(ErrorMessages.ERROR_IN_PROCESSING);
        }
    }

    @Override
    public List<ProductResponseDTO> findProductsByUserId(Long userId, String token) {
        try{
            validateToken(token);
            return productRepository.findByUserId(userId).stream().map(mapper::toDto).collect(Collectors.toList());
        }catch(Exception e){
            throw new RuntimeException(ErrorMessages.ERROR_IN_PROCESSING);
        }
    }

    public List<ProductResponseDTO> findProductsByUserCommunities(Long userId, String token) {
        try{
            //fetch user's communities
            validateToken(token);
            List<Long> communitiesId = client.getUserCommunityIds(userId, token);
            System.out.println("Printing all community Ids of users :" + communitiesId);
            List<ProductResponseDTO> productsList = new ArrayList<>();
            List<ProductResponseDTO> communityProductsList = new ArrayList<>();
            for(Long community: communitiesId){
                communityProductsList = findProductsByCommunityId(community,token);
                for(ProductResponseDTO product : communityProductsList){
                    productsList.add(product);
                }
            }
            return productsList;
        } catch (Exception e) {
            throw new RuntimeException("Error fetching products list for user");
        }
    }

    @Override
    @Transactional
    public ProductResponseDTO saveProduct(ProductRequestDTO product, String token) {
        try{
            validateToken(token);
            Product newProduct = mapper.toEntity(product);
            productRepository.save(newProduct);
            return mapper.toDto(newProduct);
        }catch(Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public ProductResponseDTO updateProduct(Product product, String token) {
        Optional<Product> optionalProduct = productRepository.findById(product.getProductId());
        try{
            validateToken(token);
            if (optionalProduct.isEmpty()) {
                throw new EntityNotFoundException(ErrorMessages.PRODUCT_DOES_NOT_EXIST);
            }
            Product existingProduct = optionalProduct.get();
            existingProduct.setCommunityId(product.getCommunityId());
            existingProduct.setUserId(product.getUserId());
            existingProduct.setTag(product.getTag());
            existingProduct.setSize(product.getSize());
            existingProduct.setCount(product.getCount());
            existingProduct.setImage(product.getImage());
            existingProduct.setName(product.getName());
            existingProduct.setDescription(product.getDescription());
            existingProduct.setColor(product.getColor());
            productRepository.save(existingProduct);
            return mapper.toDto(existingProduct);
        }catch(Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Boolean deleteProduct(Long productId, String token) {
        try{
            validateToken(token);
            productRepository.deleteById(productId);
            return true;
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }
}
