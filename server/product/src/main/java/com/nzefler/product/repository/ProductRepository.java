package com.nzefler.product.repository;

import com.nzefler.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByUserId(Long userId);
    List<Product> findByCommunityId(Long communityId);
}
