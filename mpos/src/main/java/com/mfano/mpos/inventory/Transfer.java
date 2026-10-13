package com.mfano.mpos.inventory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.mfano.mpos.dtos.OrderStatus;
import com.mfano.mpos.models.BaseObject;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "transfers")
@Setter
@Getter
@Builder
public class Transfer extends BaseObject {
    private String user;

    private String transferNumber;
    private Long fromStoreId;
    private Long toStoreId;
    private String comment;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    @Builder.Default
    private OrderStatus status = OrderStatus.PENDING;

    @Builder.Default
    @OneToMany(mappedBy = "transfer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TransferItem> items = new ArrayList<>();

    public Transfer(String user, String transferNumber, Long fromStoreId, Long toStoreId, String comment,
            OrderStatus status, List<TransferItem> items) {
        this.user = user;
        this.transferNumber = transferNumber;
        this.fromStoreId = fromStoreId;
        this.toStoreId = toStoreId;
        this.comment = comment;
        this.status = status;
        this.items = items;
    }

    public Transfer() {
    }

    public BigDecimal getTotal() {
        return items.stream()
                .map(TransferItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void addItem(TransferItem item) {
        item.setTransfer(this);
        items.add(item);
    }

    public void removeItem(Long productId) {
        items.removeIf(item -> item.getProduct().getId().equals(productId));
    }

    public void clear() {
        items.clear();
    }

    @PrePersist
    protected void onCreate() {
        if (transferNumber == null) {
            transferNumber = UUID.randomUUID()
                    .toString()
                    .replace("-", "")
                    .substring(0, 6)
                    .toUpperCase();
        }
    }
}
