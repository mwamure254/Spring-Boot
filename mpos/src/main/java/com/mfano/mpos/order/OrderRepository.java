package com.mfano.mpos.order;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mfano.mpos.dtos.OrderStatus;
import com.mfano.mpos.models.security.User;

public interface OrderRepository extends JpaRepository<Order, Long>{
   Optional<Order> findByOrderNumber(String orderNumber);

    List<Order> findByUserOrderByCreatedAtDesc(User user);

    List<Order> findByUserIdOrderByCreatedAtDesc(Long userId);

    List<Order> findByStatusOrderByCreatedAtDesc(
            OrderStatus status
    );
}
