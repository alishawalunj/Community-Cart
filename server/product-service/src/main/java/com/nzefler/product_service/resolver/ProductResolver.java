package com.nzefler.product_service.resolver;

import com.nzefler.product_service.model.Product;
import com.nzefler.product_service.repository.ProductRepository;
import com.nzefler.product_service.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class ProductResolver {

    @Autowired
    private ProductService productService;

    @QueryMapping
    public List<Product> getAllProducts(){
        return productService.getAllProducts();
    }

    @QueryMapping
    public Product getProductById(@Argument Long productId){
        return productService.getProductById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
    }

    @MutationMapping
    public Product createProduct(@Argument Product product){
        return productService.createProduct(product);
    }

    @MutationMapping
    public Product updateProduct(@Argument Product product){
        return productService.updateProduct(product);
    }

    @MutationMapping
    public String deleteProduct(@Argument Long id){
        productService.deleteProduct(id);
        return "Product deleted successfully!!";
    }
}
