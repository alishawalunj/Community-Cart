package com.nzefler.product_service.controller;

import com.nzefler.product_service.dto.ProductRequestDTO;
import com.nzefler.product_service.dto.ProductResponseDTO;
import com.nzefler.product_service.model.Product;
import com.nzefler.product_service.service.ProductServiceImpl;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class ProductController {

    private final ProductServiceImpl productService;

    public ProductController(ProductServiceImpl productService) {
        this.productService = productService;
    }

    @QueryMapping
    public List<ProductResponseDTO> getAllProducts(){
        return productService.findAllProducts();
    }

    @QueryMapping
    public ProductResponseDTO getProductById(@Argument Long productId){
        return productService.findProductById(productId);
    }

    @QueryMapping
    public List<ProductResponseDTO> getProductsByCommunityId(@Argument Long communityId){
        return productService.findProductsByCommunityId(communityId);
    }

    @QueryMapping
    public List<ProductResponseDTO> getProductsByUserId(@Argument Long userId){
        return productService.findProductsByUserId(userId);
    }

    @QueryMapping List<ProductResponseDTO> getProductsByUserCommunities(@Argument Long userId){
        return productService.findProductsByUserCommunities(userId);
    }

    @MutationMapping
    public ProductResponseDTO createProduct(@Argument ProductRequestDTO product){
        return productService.saveProduct(product);
    }

    @MutationMapping
    public ProductResponseDTO updateProduct(@Argument Product product){
        return productService.updateProduct(product);
    }

    @MutationMapping
    public Boolean deleteProduct(@Argument Long productId){
        return productService.deleteProduct(productId);
    }
}
