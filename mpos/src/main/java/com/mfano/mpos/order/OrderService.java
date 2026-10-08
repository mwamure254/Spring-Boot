package com.mfano.mpos.order;

import com.mfano.mpos.repositories.security.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mfano.mpos.cart.Cart;
import com.mfano.mpos.models.Product;
import com.mfano.mpos.cart.CartItem;
import com.mfano.mpos.cart.CartRepository;
import com.mfano.mpos.config.CustomUserDetails;
import com.mfano.mpos.dtos.OrderStatus;
import com.mfano.mpos.models.security.User;
import com.mfano.mpos.services.security.UserService;

@Transactional
@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final UserService userService;

    public OrderService(OrderRepository orderRepository, CartRepository cartRepository,
            UserService userService) {
        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
        this.userService = userService;
    }

    public Order checkout(CustomUserDetails user, String pm, RedirectAttributes red) {
        User staff = userService.findById(user.getId());
        Cart cart = cartRepository.findByUser(staff)
                .orElseThrow(() -> new IllegalStateException("Cart not found"));

        if (cart.getItems().isEmpty()) {
            red.addFlashAttribute(
                    "error", "Cannot checkout an empty cart");
                    
        }

        Order order = Order.builder()
                .pm(pm)
                .status(OrderStatus.PENDING)
                .build();

       order.setCreatedBy(user.getEmail());

        for (CartItem cartItem : cart.getItems()) {
            Product product = cartItem.getProduct();

            if (product.getStockQuantity() < cartItem.getQuantity()) {
            red.addFlashAttribute(
                    "error", "Insufficient stock for product: "
                                + product.getName());
            }

            OrderItem orderItem = OrderItem.builder()
                    .product(cartItem.getProduct())
                    .quantity(cartItem.getQuantity())
                    .unitPrice(cartItem.getProduct().getPrice())
                    .build();

            order.addItem(orderItem);
            order.setStatus(OrderStatus.COMPLETED);

            // Reduce stock
            product.setStockQuantity(
                    product.getStockQuantity()
                            - cartItem.getQuantity());
        }

        Order savedOrder = orderRepository.save(order);

        cart.getItems().clear();
        cartRepository.save(cart);

        return savedOrder;
    }

    public Order getById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }
}
