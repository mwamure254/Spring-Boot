package com.mfano.mpos.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mfano.mpos.dtos.GlobalSearchResult;
import com.mfano.mpos.models.Product;

import jakarta.persistence.LockModeType;

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

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT p FROM Product p WHERE p.id = :id")
    Optional<Product> findByIdForUpdate(@Param("id") Long id);

    // Count products currently in stock
    long countByActiveTrueAndStockQuantityGreaterThan(int quantity);

    // Sum the available stock quantities
    @Query("""
        SELECT COALESCE(SUM(p.stockQuantity), 0)
        FROM Product p
        WHERE p.active = true
          AND p.stockQuantity > 0
    """)
    Long sumInStockQuantity();

    // Sum stock quantities for a specific branch
    @Query("""
        SELECT COALESCE(SUM(p.stockQuantity), 0)
        FROM Product p
        WHERE p.active = true
          AND p.stockQuantity > 0
          AND p.branch.id = :branchId
    """)
    Long sumInStockQuantityByBranch(@Param("branchId") Long branchId);

    List<Product> findByNameContainingIgnoreCaseOrSkuContainingIgnoreCase(String keyword, String keyword2);
}