package com.mfano.mpos.order;

import org.springframework.stereotype.Service;

import com.mfano.mpos.models.Product;
import com.mfano.mpos.repositories.ProductRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;

    public OrderItem addItem(
            Order order,
            Product product,
            int quantity) {

        if (quantity <= 0) {
            throw new IllegalArgumentException(
                    "Quantity must be greater than zero");
        }

        product = productRepository
                .findByIdForUpdate(product.getId())
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));

        // Check and reduce stock
        product.reduceStock(quantity);

        // Create order item
        OrderItem item = new OrderItem();
        item.setOrder(order);
        item.setProduct(product);
        item.setQuantity(quantity);

        // Capture current price
        item.setUnitPrice(product.getPrice());

        // Calculate subtotal
        item.calculateSubtotal();

        // Save order item
        return orderItemRepository.save(item);
    }
}
