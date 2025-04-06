package com.nzefler.product_service.repository;

import com.nzefler.product_service.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByUserId(Long userId);
    List<Product> findByCommunityId(Long communityId);
}
