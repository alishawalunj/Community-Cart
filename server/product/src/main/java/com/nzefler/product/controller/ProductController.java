package com.nzefler.product.controller;

import com.nzefler.product.dto.ProductRequestDTO;
import com.nzefler.product.dto.ProductResponseDTO;
import com.nzefler.product.model.Product;
import com.nzefler.product.service.ProductServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product-service")
public class ProductController {

    private final ProductServiceImpl productService;

    public ProductController(ProductServiceImpl productService) {
        this.productService = productService;
    }

    @GetMapping("/getAllProducts")
    public List<ProductResponseDTO> getAllProducts(){
        return productService.findAllProducts();
    }

    @GetMapping("/getProductById/{productId}")
    public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable Long productId){
        ProductResponseDTO response = productService.findProductById(productId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getProductsByCommunityId/{communityId}")
    public List<ProductResponseDTO> getProductsByCommunityId(@PathVariable Long communityId){
        return productService.findProductsByCommunityId(communityId);
    }

    @GetMapping("/getProductsByUserId/{userId}")
    public List<ProductResponseDTO> getProductsByUserId(@PathVariable Long userId){
        return productService.findProductsByUserId(userId);
    }

    @GetMapping("/getProductsByUserCommunities/{userId}")
    public List<ProductResponseDTO> getProductsByUserCommunities(@PathVariable Long userId){
        return productService.findProductsByUserCommunities(userId);
    }

   @PostMapping("/createProduct")
    public ResponseEntity<ProductResponseDTO> createProduct(@RequestBody ProductRequestDTO product){
       ProductResponseDTO response = productService.saveProduct(product);
       return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/updateProduct")
    public ResponseEntity<ProductResponseDTO> updateProduct(@RequestBody Product product){
        ProductResponseDTO response =  productService.updateProduct(product);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/deleteProduct/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId){
        productService.deleteProduct(productId);
        return ResponseEntity.noContent().build();
    }
}
