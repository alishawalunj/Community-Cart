package com.nzefler.product_service.service;

import com.nzefler.product_service.client.CommunityGraphQLClient;
import com.nzefler.product_service.dto.ProductRequestDTO;
import com.nzefler.product_service.dto.ProductResponseDTO;
import com.nzefler.product_service.exception.EntityNotFoundException;
import com.nzefler.product_service.mapper.ProductMapper;
import com.nzefler.product_service.model.Product;
import com.nzefler.product_service.repository.ProductRepository;
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
    private final CommunityGraphQLClient client;

    public ProductServiceImpl(ProductRepository productRepository, ProductMapper mapper,CommunityGraphQLClient client) {
        this.productRepository = productRepository;
        this.mapper = mapper;
        this.client = client;
    }

    @Override
    public List<ProductResponseDTO> findAllProducts() {
        try{
            return productRepository.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
        }catch(Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public ProductResponseDTO findProductById(Long productId) {
        try{
            return productRepository.findById(productId).map(mapper::toDto).orElseThrow(() -> new EntityNotFoundException("Product does not exist"));
        }catch(Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<ProductResponseDTO> findProductsByCommunityId(Long communityId) {
        try{
            return productRepository.findByCommunityId(communityId).stream().map(mapper::toDto).collect(Collectors.toList());
        }catch(Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<ProductResponseDTO> findProductsByUserId(Long userId) {
        try{
            return productRepository.findByUserId(userId).stream().map(mapper::toDto).collect(Collectors.toList());
        }catch(Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<ProductResponseDTO> findProductsByUserCommunities(Long userId) {
        try{
            //fetch user's communities
            List<Long> communities = client.getUserCommunityIds(userId);
            List<ProductResponseDTO> productsList = new ArrayList<>();
            List<ProductResponseDTO> communityProductsList = new ArrayList<>();
            for(Long community: communities){
                communityProductsList = findProductsByCommunityId(community);
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
    public ProductResponseDTO saveProduct(ProductRequestDTO product) {
        try{
            Product newProduct = mapper.toEntity(product);
            productRepository.save(newProduct);
            return mapper.toDto(newProduct);
        }catch(Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public ProductResponseDTO updateProduct(Product product) {
        Optional<Product> optionalProduct = productRepository.findById(product.getProductId());
        try{
            if (optionalProduct.isEmpty()) {
                throw new EntityNotFoundException("Product does not exist");
            }
            Product existingProduct = optionalProduct.get();
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
    public Boolean deleteProduct(Long productId) {
        try{
            productRepository.deleteById(productId);
            return true;
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

}
