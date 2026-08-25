package com.mfano.mpos.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mfano.mpos.models.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{

    Optional<Product> findBySku(String sku);

    boolean existsBySku(String sku);

    List<Product> findByActiveTrue();

    List<Product> findByNameContainingIgnoreCaseAndActiveTrue(
            String name
    );

    List<Product> findByStockQuantityLessThanEqual(
            Integer quantity
    );
}
