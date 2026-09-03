package com.mfano.mpos.dtos;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.mfano.mpos.models.Product;

public class Cart {

    private final List<CartItem> items = new ArrayList<>();

    public void addItem(Product product, int quantity) {

        if (product == null) {
            throw new IllegalArgumentException("Product is required");
        }

        if (quantity <= 0) {
            throw new IllegalArgumentException(
                    "Quantity must be greater than zero"
            );
        }

        CartItem existing = items.stream()
                .filter(item -> item.getProduct().getId()
                        .equals(product.getId()))
                .findFirst()
                .orElse(null);

        if (existing != null) {

            int newQuantity =
                    existing.getQuantity() + quantity;

            if (newQuantity > product.getStockQuantity()) {
                throw new IllegalStateException(
                        "Insufficient product quantity"
                );
            }

            existing.setQuantity(newQuantity);

        } else {

            if (quantity > product.getStockQuantity()) {
                throw new IllegalStateException(
                        "Insufficient product quantity"
                );
            }

            items.add(new CartItem(
                    product,
                    quantity,
                    product.getPrice()
            ));
        }
    }

    public void removeItem(Long productId) {
        items.removeIf(item ->
                item.getProduct().getId().equals(productId)
        );
    }

    public void clear() {
        items.clear();
    }

    public List<CartItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    public BigDecimal grandTotal() {
        return items.stream()
                .map(CartItem::total)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
