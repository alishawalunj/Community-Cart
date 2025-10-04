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
    public List<ProductResponseDTO> getAllProducts(@RequestHeader("Authorization") String token){
        System.out.println(token);
        return productService.findAllProducts(token);
    }

    @GetMapping("/getProductById/{productId}")
    public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable Long productId, @RequestHeader("Authorization") String token){
        ProductResponseDTO response = productService.findProductById(productId, token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getProductsByCommunityId/{communityId}")
    public List<ProductResponseDTO> getProductsByCommunityId(@PathVariable Long communityId, @RequestHeader("Authorization") String token){
        return productService.findProductsByCommunityId(communityId, token);
    }

    @GetMapping("/getProductsByUserId/{userId}")
    public List<ProductResponseDTO> getProductsByUserId(@PathVariable Long userId, @RequestHeader("Authorization") String token){
        return productService.findProductsByUserId(userId, token);
    }

    @GetMapping("/getProductsByUserCommunities/{userId}")
    public List<ProductResponseDTO> getProductsByUserCommunities(@PathVariable Long userId, @RequestHeader("Authorization") String token){
        return productService.findProductsByUserCommunities(userId, token);
    }

   @PostMapping("/createProduct")
    public ResponseEntity<ProductResponseDTO> createProduct(@RequestBody ProductRequestDTO product, @RequestHeader("Authorization") String token){
       ProductResponseDTO response = productService.saveProduct(product, token);
       return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/updateProduct")
    public ResponseEntity<ProductResponseDTO> updateProduct(@RequestBody Product product, @RequestHeader("Authorization") String token){
        ProductResponseDTO response =  productService.updateProduct(product, token);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/deleteProduct/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId, @RequestHeader("Authorization") String token){
        productService.deleteProduct(productId, token);
        return ResponseEntity.noContent().build();
    }
}
