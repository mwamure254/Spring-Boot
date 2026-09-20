package com.mfano.mpos.cart;

import org.springframework.stereotype.Service;

import com.mfano.mpos.models.Product;
import com.mfano.mpos.config.CustomUserDetails;
import com.mfano.mpos.models.security.User;
import com.mfano.mpos.services.security.UserService;
import com.mfano.mpos.repositories.ProductRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Transactional
@RequiredArgsConstructor
@Service
public class CartService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserService userService;

    public Cart getCart(User user) {
        return cartRepository.findByUser(user)
                .orElseGet(() -> createCart(user));
    }

    private Cart createCart(User user) {
        Cart cart = Cart.builder()
                .user(user)
                .build();

        return cartRepository.save(cart);
    }

    public Cart addToCart(CustomUserDetails auth, Long productId, int quantity) {
        User user = userService.findById(auth.getId());
        if (quantity <= 0) {
            throw new IllegalArgumentException(
                    "Quantity must be greater than zero"
            );
        }

        Product product = productRepository.findById(productId)
                .orElseThrow(() ->
                        new RuntimeException("Product not found"));
        Cart cart = getCart(user);
        cart.addItem(product, quantity);
        return cartRepository.save(cart);
    }

    public void removeFromCart(CustomUserDetails auth, Long id) {
        User user = userService.findById(auth.getId());
        Cart cart = getCart(user);
        cart.removeItem(id);
        cartRepository.save(cart);
    }

    public void clearCart(CustomUserDetails auth) {
        User user = userService.findById(auth.getId());
        Cart cart = getCart(user);
        cart.clear();
        cartRepository.save(cart);
    }
}
