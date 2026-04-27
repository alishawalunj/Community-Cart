package com.nzefler.product.controller;

import com.nzefler.product.dto.ProductRequestDTO;
import com.nzefler.product.dto.ProductResponseDTO;
import com.nzefler.product.dto.StockUpdateDTO;
import com.nzefler.product.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    private Long currentUserId() {
        return (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductResponseDTO>> findAll() {
        return ResponseEntity.ok(productService.findAllInUserCommunities(currentUserId()));
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<ProductResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.findById(id, currentUserId()));
    }

    @PostMapping("/products")
    public ResponseEntity<ProductResponseDTO> create(@RequestBody ProductRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productService.create(currentUserId(), request));
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<ProductResponseDTO> update(@PathVariable Long id, @RequestBody ProductRequestDTO request) {
        return ResponseEntity.ok(productService.update(id, currentUserId(), request));
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.delete(id, currentUserId());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/communities/{cId}/products")
    public ResponseEntity<List<ProductResponseDTO>> findByCommunity(@PathVariable Long cId) {
        return ResponseEntity.ok(productService.findByCommunity(cId, currentUserId()));
    }

    @GetMapping("/users/{uId}/products")
    public ResponseEntity<List<ProductResponseDTO>> findByUser(@PathVariable Long uId) {
        return ResponseEntity.ok(productService.findByUser(uId));
    }

    @PatchMapping("/products/{id}/stock")
    public ResponseEntity<Void> decrementStock(@PathVariable Long id, @RequestBody StockUpdateDTO stockUpdate) {
        productService.decrementStock(id, stockUpdate);
        return ResponseEntity.noContent().build();
    }
}