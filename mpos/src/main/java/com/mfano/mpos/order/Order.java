
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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "orders")
@Getter
@Setter
@Builder
@AllArgsConstructor
public class Order extends BaseObject {
    @Column(nullable = false, unique = true, updatable = false)
    private String orderNumber;

    private String pm;

    // Monetary discount in KES
    @Builder.Default
    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal discount = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    @Builder.Default
    private OrderStatus status = OrderStatus.PENDING;

    @OneToMany(
        fetch = FetchType.LAZY,
        mappedBy = "order",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    @Builder.Default
    private List<OrderItem> items = new ArrayList<>();

    public Order(
            String orderNumber,
            String pm,
            OrderStatus status,
            List<OrderItem> items,
            BigDecimal discount) {
        this.orderNumber = orderNumber;
        this.pm = pm;
        this.status = status;
        this.items = items != null ? items : new ArrayList<>();
        this.discount = BigDecimal.ZERO;
    }

    public Order() {
    }

    /**
     * Calculate the total before discount.
     */
    public BigDecimal getSubtotal() {
        if (items == null) {
            return BigDecimal.ZERO;
        }

        return items.stream()
                .map(OrderItem::getSubtotal)
                .filter(subtotal -> subtotal != null)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Calculate the final amount payable after discount.
     */
    public BigDecimal getTotal() {
        BigDecimal subtotal = getSubtotal();
        BigDecimal actualDiscount =
                discount != null ? discount : BigDecimal.ZERO;

        // Prevent negative order totals.
        if (actualDiscount.compareTo(BigDecimal.ZERO) < 0) {
            actualDiscount = BigDecimal.ZERO;
        }

        if (actualDiscount.compareTo(subtotal) > 0) {
            actualDiscount = subtotal;
        }

        return subtotal.subtract(actualDiscount);
    }

    /**
     * Amount saved by the customer.
     */
    public BigDecimal getAmountSaved() {
        return getSubtotal().subtract(getTotal());
    }

    @PrePersist
    protected void onCreate() {
        if (orderNumber == null || orderNumber.isBlank()) {
            orderNumber = UUID.randomUUID()
                    .toString()
                    .replace("-", "")
                    .substring(0, 6)
                    .toUpperCase();
        }

        if (discount == null) {
            discount = BigDecimal.ZERO;
        }

        if (items == null) {
            items = new ArrayList<>();
        }
    }

    public void addItem(OrderItem item) {
        if (items == null) {
            items = new ArrayList<>();
        }

        item.setOrder(this);
        items.add(item);
    }
}
