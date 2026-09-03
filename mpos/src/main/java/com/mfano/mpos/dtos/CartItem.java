package com.mfano.mpos.dtos;

import java.math.BigDecimal;

import com.mfano.mpos.models.Product;

public class CartItem {

    private final Product product;
    private int quantity;
    private final BigDecimal unitPrice;

    public CartItem(
            Product product,
            int quantity,
            BigDecimal unitPrice
    ) {
        this.product = product;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public BigDecimal total() {
        return unitPrice.multiply(
                BigDecimal.valueOf(quantity)
        );
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException(
                    "Quantity must be greater than zero"
            );
        }

        this.quantity = quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }
}
