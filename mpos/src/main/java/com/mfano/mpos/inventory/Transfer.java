package com.mfano.mpos.inventory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.mfano.mpos.models.BaseObject;
import com.mfano.mpos.models.Product;
import com.mfano.mpos.models.security.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tranfers")
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transfer extends BaseObject {
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    private String transferNumber;
    private Long fromStoreId;
    private Long toStoreId;

    @OneToMany(mappedBy = "transfer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TransferItem> items = new ArrayList<>();

    public BigDecimal getTotal() {
        return items.stream()
                .map(TransferItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void addItem(Product product, int quantity) {
        items.stream()
                .filter(item -> item.getProduct().getId().equals(product.getId()))
                .findFirst()
                .ifPresentOrElse(
                        item -> item.setQuantity(item.getQuantity() + quantity),
                        () -> {
                            TransferItem item = TransferItem.builder()
                                    .product(product)
                                    .quantity(quantity)
                                    .build();

                            items.add(item);
                        });
    }

    public void removeItem(Long productId) {
        items.removeIf(item -> item.getProduct().getId().equals(productId));
    }

    public void clear() {
        items.clear();
    }

    private String comment;
}
