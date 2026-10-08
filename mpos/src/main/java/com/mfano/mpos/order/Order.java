package com.mfano.mpos.order;

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
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "orders")
@Getter
@Setter
@Builder
public class Order extends BaseObject{
    @Column(nullable = false, unique = true, updatable = false)
    private String orderNumber;
    private String pm;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    @Builder.Default
    private OrderStatus status = OrderStatus.PENDING;

     @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "order",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @Builder.Default
    private List<OrderItem> items = new ArrayList<>();

    public Order(String orderNumber, String pm, OrderStatus status, List<OrderItem> items) {
        this.orderNumber = orderNumber;
        this.pm = pm;
        this.status = status;
        this.items = items;
    }

    public Order() {
    }

    public BigDecimal getTotal() {
        return items.stream()
                .map(OrderItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @PrePersist
    protected void onCreate() {
        if (orderNumber == null) {
            orderNumber = UUID.randomUUID()
                    .toString()
                    .replace("-", "")
                    .substring(0, 6)
                    .toUpperCase();
        }
    }

    public void addItem(OrderItem item) {
        item.setOrder(this);
        items.add(item);
    }

}
