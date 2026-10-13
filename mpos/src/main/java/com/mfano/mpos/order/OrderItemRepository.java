package com.mfano.mpos.order;

import java.util.List;
import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mfano.mpos.dtos.OrderStatus;
import com.mfano.mpos.dtos.TopSellingProduct;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    List<OrderItem> findByOrderId(Long orderId);
     List<OrderItem> findByOrder_Id(Long orderId);

    List<OrderItem> findByOrder_OrderNumber(String orderNumber);

    @Query("""
        SELECT new com.mfano.mpos.dtos.TopSellingProduct(
            p.id,
            p.name,
            p.sku,
            p.price,
            SUM(i.quantity),
            SUM(i.subtotal),
            p.image
        )
        FROM OrderItem i
        JOIN i.product p
        JOIN i.order o
        WHERE o.status = :status
          AND o.createdAt >= :start
          AND o.createdAt < :end
        GROUP BY p.id, p.name, p.sku, p.price, p.image
        ORDER BY SUM(i.quantity) DESC
    """)
    List<TopSellingProduct> findTop10SellingProducts(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end,
            @Param("status") OrderStatus status);
}