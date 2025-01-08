package com.nzefler.product_service.service;

import com.nzefler.product_service.model.Product;

import java.util.List;

public class ProductServiceImpl implements ProductService{
    @Override
    public List<Product> getAllProducts() {
        return List.of();
    }

    @Override
    public Product getProductById(Long productId) {
        return null;
    }

    @Override
    public Product createProduct(Product product) {
        return null;
    }

    @Override
    public Product updateProduct(Product product) {
        return null;
    }

    @Override
    public void deleteProduct(Long productId) {

    }
}
