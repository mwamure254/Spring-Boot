package com.mfano.mpos.order;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mfano.mpos.cart.Cart;
import com.mfano.mpos.cart.CartItem;
import com.mfano.mpos.cart.CartRepository;
import com.mfano.mpos.dtos.OrderStatus;
import com.mfano.mpos.models.security.User;
import com.mfano.mpos.repositories.ProductRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;

    public Order checkout(User user) {

        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() ->
                        new IllegalStateException("Cart not found"));

        if (cart.getItems().isEmpty()) {
            throw new IllegalStateException(
                    "Cannot checkout an empty cart"
            );
        }

        Order order = Order.builder()
                .user(user)
                .status(OrderStatus.PENDING)
                .build();

        for (CartItem cartItem : cart.getItems()) {

            OrderItem orderItem = OrderItem.builder()
                    .product(cartItem.getProduct())
                    .quantity(cartItem.getQuantity())
                    .unitPrice(cartItem.getProduct().getPrice())
                    .build();

            order.addItem(orderItem);
        }

        order.calculateTotals();

        Order savedOrder = orderRepository.save(order);

        cart.clear();
        cartRepository.save(cart);

        return savedOrder;
    }

     public Order getById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }
}
