package com.nzefler.product_service.service;

import com.nzefler.product_service.model.Product;

import java.util.List;

public interface ProductService {

    List<Product> getAllProducts();
    Product getProductById(Long productId);
    Product createProduct(Product product);
    Product updateProduct(Product product);
    void deleteProduct(Long productId);

}
