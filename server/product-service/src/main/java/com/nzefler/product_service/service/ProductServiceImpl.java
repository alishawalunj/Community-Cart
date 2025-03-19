package com.nzefler.product_service.service;

import com.nzefler.product_service.dto.ProductResponseDTO;
import com.nzefler.product_service.mapper.ProductMapper;
import com.nzefler.product_service.model.Product;
import com.nzefler.product_service.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper mapper;

    @Override
    public List<ProductResponseDTO> findAllProducts() {
        try{
            List<ProductResponseDTO> productList = new ArrayList<>();
            List<Product> products = productRepository.findAll();
            for(Product product: products){
                productList.add(mapper.toDto(product));
            }
            return productList;
        }catch(Exception e){
            throw new RuntimeException("Error fetching products");
        }
    }

    @Override
    public ProductResponseDTO findProductById(Long productId) {
        try{
            return productRepository.findById(productId).map(mapper::toDto).orElseThrow(() -> new RuntimeException("No product present in database with id "+ productId));
        }catch(Exception e){
            throw new RuntimeException("Error fetching product");
        }
    }

    @Override
    public List<ProductResponseDTO> findProductsByCommunityId(Long communityId) {
        try{
            List<ProductResponseDTO> productList = new ArrayList<>();
            List<Product> products = productRepository.findAllByCommunityId(communityId);
            for(Product product: products){
                productList.add(mapper.toDto(product));
            }
            return productList;
        }catch(Exception e){
            throw new RuntimeException("Error fetching products");
        }
    }

    @Override
    public List<ProductResponseDTO> findProductsByUserId(Long userId) {
        try{
            List<ProductResponseDTO> productList = new ArrayList<>();
            List<Product> products = productRepository.findAllByUserId(userId);
            for(Product product: products){
                productList.add(mapper.toDto(product));
            }
            return productList;
        }catch(Exception e){
            throw new RuntimeException("Error fetching products");
        }
    }

    @Override
    public String saveProduct(Product product) {
        try{
            Optional<Product> existingProduct = productRepository.findById(product.getProductId());
            if(existingProduct.isPresent()){
                throw new RuntimeException("Product already exists");
            }else{
                productRepository.save(product);
                return "Product created successsfully";
            }
        }catch(Exception e){
            throw new RuntimeException("Error creating new product");
        }
    }

    @Override
    public Product updateProduct(Product product) {
        try{
            Optional<Product> optionalProduct = productRepository.findById(product.getProductId());
            if (optionalProduct.isEmpty()) {
                throw new RuntimeException("Product does not exist");
            }
            Product existingProduct = optionalProduct.get();
            Product updatedProduct = mapper.updateExistingProduct(existingProduct,product);
            return productRepository.save(updatedProduct);
        }catch(Exception e){
            throw new RuntimeException("Error creating new product");
        }
    }

    @Override
    public void deleteProduct(Long productId) {
        try{
            productRepository.deleteById(productId);
        }catch (Exception e){
            throw new RuntimeException("Error deleting product");
        }

    }
}
