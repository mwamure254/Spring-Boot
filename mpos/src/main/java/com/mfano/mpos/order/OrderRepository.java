package com.mfano.mpos.order;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mfano.mpos.dtos.OrderStatus;

public interface OrderRepository extends JpaRepository<Order, Long>{
   Optional<Order> findByOrderNumber(String orderNumber);

    List<Order> findByCreatedByOrderByCreatedAtDesc(String user);

    List<Order> findByStatusOrderByCreatedAtDesc(
            OrderStatus status
    );
}
