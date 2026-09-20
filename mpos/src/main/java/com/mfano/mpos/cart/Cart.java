package com.mfano.mpos.cart;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.mfano.mpos.models.Product;
import com.mfano.mpos.models.security.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "carts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @OneToMany(
        mappedBy = "cart",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    @Builder.Default
    private List<CartItem> items = new ArrayList<>();

    public BigDecimal getTotal() {
        return items.stream()
                .map(CartItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void addItem(Product product, int quantity) {
    
    CartItem existingItem = items.stream()
            .filter(item -> item.getProduct().getId().equals(product.getId()))
            .findFirst()
            .orElse(null);

    if (existingItem != null) {
        existingItem.setQuantity(existingItem.getQuantity() + quantity);
    } else {
        CartItem item = new CartItem();
        item.setCart(this);
        item.setProduct(product);
        item.setQuantity(quantity);

        items.add(item);
    }
}

    public void removeItem(Long id) {
        items.removeIf(item ->
                item.getId().equals(id)
        );
    }

    public void clear() {
        items.clear();
    }
}
