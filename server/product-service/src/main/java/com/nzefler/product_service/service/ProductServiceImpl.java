package com.nzefler.product_service.service;

import com.nzefler.product_service.model.Product;
import com.nzefler.product_service.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Optional<Product> getProductById(Long productId) {
        return productRepository.findById(productId);
    }

    @Override
    public Product createProduct(Product product) {
        if(product.getProductId() != null){
            throw new RuntimeException("Product already exists!!");
        }
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Product product) {
        Optional<Product> optionalProduct = productRepository.findById(product.getProductId());
        if(optionalProduct.isEmpty()){
            throw new RuntimeException("Product does not exist, please add the product first");
        }
        Product existingProduct = optionalProduct.get();
        existingProduct.setCategory(product.getCategory());
        existingProduct.setColor(product.getColor());
        existingProduct.setName(product.getName());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setCommunityId(product.getCommunityId());
        existingProduct.setSize(product.getSize());
        existingProduct.setTag(product.getTag());
        existingProduct.setType(product.getType());
        existingProduct.setUserId(product.getUserId());
        productRepository.save(existingProduct);
        return existingProduct;
    }

    @Override
    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }
}
