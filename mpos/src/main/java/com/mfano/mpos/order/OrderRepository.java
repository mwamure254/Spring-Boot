package com.mfano.mpos.order;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mfano.mpos.dtos.OrderStatus;
import com.mfano.mpos.models.security.User;

public interface OrderRepository extends JpaRepository<Order, Long>{
 @Query("select sum(o.total) from Order o where o.store.id = :storeId and o.createdAt >= current_date")
    BigDecimal sumTodayByStore(Long storeId);

     Optional<Order> findByOrderNumber(String orderNumber);

    List<Order> findByUserOrderByCreatedAtDesc(User user);

    List<Order> findByUserIdOrderByCreatedAtDesc(Long userId);

    List<Order> findByStatusOrderByCreatedAtDesc(
            OrderStatus status
    );
}
