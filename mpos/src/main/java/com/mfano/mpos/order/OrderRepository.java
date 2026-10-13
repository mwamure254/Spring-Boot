package com.mfano.mpos.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mfano.mpos.dtos.OrderStatus;
import com.mfano.mpos.dtos.PaymentMethodReport;

public interface OrderRepository extends JpaRepository<Order, Long> {
        Optional<Order> findByOrderNumber(String orderNumber);

        List<Order> findByCreatedByOrderByCreatedAtDesc(String user);

        List<Order> findByStatusOrderByCreatedAtDesc(
                        OrderStatus status);

        long countByStatus(OrderStatus status);

        @Query("""
                            SELECT DISTINCT o
                            FROM Order o
                            LEFT JOIN FETCH o.items
                            WHERE o.createdAt >= :start
                              AND o.createdAt < :end
                              AND o.status = :status
                        """)
        List<Order> findOrdersForAnalytics(
                        @Param("start") LocalDateTime start,
                        @Param("end") LocalDateTime end,
                        @Param("status") OrderStatus status);

        @Query("""
                            SELECT COUNT(DISTINCT o.id)
                            FROM Order o
                            WHERE o.createdAt >= :start
                              AND o.createdAt < :end
                              AND o.status = :status
                        """)
        long countSalesBetween(
                        @Param("start") LocalDateTime start,
                        @Param("end") LocalDateTime end,
                        @Param("status") OrderStatus status);

        @Query("""
                            SELECT COALESCE(SUM(i.subtotal), 0)
                            FROM OrderItem i
                            WHERE i.order.createdAt >= :start
                              AND i.order.createdAt < :end
                              AND i.order.status = :status
                        """)
        BigDecimal sumRevenueBetween(
                        @Param("start") LocalDateTime start,
                        @Param("end") LocalDateTime end,
                        @Param("status") OrderStatus status);

        @Query("""
                            SELECT COALESCE(SUM(i.quantity), 0)
                            FROM OrderItem i
                            WHERE i.order.createdAt >= :start
                              AND i.order.createdAt < :end
                              AND i.order.status = :status
                        """)
        Long sumQuantitySoldBetween(
                        @Param("start") LocalDateTime start,
                        @Param("end") LocalDateTime end,
                        @Param("status") OrderStatus status);

        List<Order> findTop10ByOrderByCreatedAtDesc();

        @Query("""
                            SELECT new com.mfano.mpos.dtos.PaymentMethodReport(
                                o.pm,
                                COUNT(DISTINCT o.id),
                                COALESCE(SUM(i.subtotal), 0)
                            )
                            FROM Order o
                            JOIN o.items i
                            GROUP BY o.pm
                            ORDER BY o.pm
                        """)
        List<PaymentMethodReport> findSalesAndRevenueByPaymentMethod();

        @Query("""
                            SELECT COALESCE(SUM(i.quantity), 0)
                            FROM Order o
                            JOIN o.items i
                            WHERE o.createdAt >= :start
                              AND o.createdAt < :end
                              AND o.status = :status
                        """)
        long countItemsBetween(
                        @Param("start") LocalDateTime start,
                        @Param("end") LocalDateTime end,
                        @Param("status") OrderStatus status);
}
