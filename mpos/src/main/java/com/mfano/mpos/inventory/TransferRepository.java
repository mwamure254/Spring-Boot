package com.mfano.mpos.inventory;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.mfano.mpos.dtos.OrderStatus;

public interface TransferRepository extends JpaRepository<Transfer, Long>{
   
   Optional<Transfer> findByTransferNumber(String orderNumber);

    List<Transfer> findByCreatedByOrderByCreatedAtDesc(String user);

    List<Transfer> findByStatusOrderByCreatedAtDesc(
            OrderStatus status
    );
}
