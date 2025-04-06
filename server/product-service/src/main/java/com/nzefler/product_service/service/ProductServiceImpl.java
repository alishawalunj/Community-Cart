package com.nzefler.product_service.service;

import com.nzefler.product_service.dto.ProductResponseDTO;
import com.nzefler.product_service.exception.EntityAlreadyExistsException;
import com.nzefler.product_service.exception.EntityNotFoundException;
import com.nzefler.product_service.mapper.ProductMapper;
import com.nzefler.product_service.model.Product;
import com.nzefler.product_service.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;
    private final ProductMapper mapper;

    public ProductServiceImpl(ProductRepository productRepository, ProductMapper mapper) {
        this.productRepository = productRepository;
        this.mapper = mapper;
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
    @Transactional
    public String saveProduct(Product product) {
        try{
            productRepository.save(product);
            return "Product created successfully";
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
            existingProduct.setProductType(product.getProductType());
            existingProduct.setSize(product.getSize());
            existingProduct.setCount(product.getCount());
            existingProduct.setName(product.getName());
            existingProduct.setDescription(product.getDescription());
            existingProduct.setCategory(product.getCategory());
            existingProduct.setColor(product.getColor());
            productRepository.save(existingProduct);
            return mapper.toDto(existingProduct);
        }catch(Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public String deleteProduct(Long productId) {
        try{
            productRepository.deleteById(productId);
            return "Product deleted successfully";
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    private void validateUserCommunities(Long userId, Set<Long> communityIds){

    }
}
