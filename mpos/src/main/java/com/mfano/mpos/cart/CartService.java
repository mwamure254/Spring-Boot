package com.mfano.mpos.cart;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mfano.mpos.models.Product;
import com.mfano.mpos.config.CustomUserDetails;
import com.mfano.mpos.models.security.User;
import com.mfano.mpos.services.security.UserService;
import com.mfano.mpos.repositories.ProductRepository;

import jakarta.transaction.Transactional;

@Transactional
@Service
public class CartService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserService userService;

    public CartService(CartRepository cartRepository, ProductRepository productRepository, UserService userService) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.userService = userService;
    }

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

    public void addToCart(CustomUserDetails auth, Long productId, int quantity, RedirectAttributes red) {
        if (quantity <= 0) {
            red.addFlashAttribute(
                    "error", "Quantity must be greater than zero");
        } else {

            User user = userService.findById(auth.getId());
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new IllegalArgumentException("Product not found"));

            Cart cart = getCart(user);

            // Check existing quantity in cart
            int existingQuantity = cart.getItems().stream()
                    .filter(item -> item.getProduct().getId().equals(productId))
                    .mapToInt(CartItem::getQuantity)
                    .findFirst()
                    .orElse(0);

            int newQuantity = existingQuantity + quantity;
            if (newQuantity > product.getStockQuantity()) {
                red.addFlashAttribute(
                        "error",
                        "Stock is not enough. Available: "
                                + product.getStockQuantity()
                                + ", already in cart: "
                                + existingQuantity);
            } else {

                cart.addItem(product, quantity);
                cartRepository.save(cart);
            }
        }
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
