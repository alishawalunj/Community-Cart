package com.nzefler.product_service.repository;

import com.nzefler.product_service.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAllByUserId(Long userId);
    List<Product> findAllByCommunityId(Long communityId);
}
