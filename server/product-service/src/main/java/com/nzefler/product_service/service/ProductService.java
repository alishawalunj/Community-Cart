package com.nzefler.product_service.service;

import com.nzefler.product_service.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<Product> getAllProducts();
    Optional<Product> getProductById(Long productId);
    Product createProduct(Product product);
    Product updateProduct(Product product);
    void deleteProduct(Long productId);

}
