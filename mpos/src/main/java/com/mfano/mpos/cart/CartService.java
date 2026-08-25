package com.mfano.mpos.cart;

import org.springframework.stereotype.Service;

import com.mfano.mpos.models.Product;
import com.mfano.mpos.models.security.User;
import com.mfano.mpos.repositories.ProductRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Transactional
@RequiredArgsConstructor
@Service
public class CartService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

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

    public Cart addToCart(User user, Long productId, int quantity) {

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

    public void removeFromCart(User user, Long productId) {

        Cart cart = getCart(user);

        cart.removeItem(productId);

        cartRepository.save(cart);
    }

    public void clearCart(User user) {

        Cart cart = getCart(user);

        cart.clear();

        cartRepository.save(cart);
    }
}
