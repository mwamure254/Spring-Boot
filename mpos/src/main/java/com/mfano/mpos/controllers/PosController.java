package com.mfano.mpos.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mfano.mpos.cart.Cart;
import com.mfano.mpos.cart.CartService;
import com.mfano.mpos.config.CustomUserDetails;
import com.mfano.mpos.order.OrderService;
import com.mfano.mpos.order.Order;
import com.mfano.mpos.services.ProductService;
import com.mfano.mpos.services.security.UserService;
import com.mfano.mpos.services.security.ProfileService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/pos")
@RequiredArgsConstructor
public class PosController {
    private final ProductService productService;
    private final OrderService orderService;
    private final CartService cartService;
    private final ProfileService profileService;
    private final UserService userService;

    @GetMapping
    public String posPage(@AuthenticationPrincipal CustomUserDetails auth, Model model) {
        model.addAttribute("profile", profileService.checkProfile(auth.getId()));
        model.addAttribute("products", productService.findAll());
        model.addAttribute("cart", cartService.getCart(userService.findById(auth.getId())));
        // model.addAttribute("cart", new Cart()); // stored in session
        return "pos/index";
    }

    @PostMapping("/cart/add")
    public String addToCart(@AuthenticationPrincipal CustomUserDetails auth, @RequestParam Long productId) {
        cartService.addToCart(auth, productId, 1);
        return "redirect:/pos";
    }

    @PostMapping("/cart/remove/{id}")
    public String removeFromCart(@AuthenticationPrincipal CustomUserDetails auth, @PathVariable Long id) {
        cartService.removeFromCart(auth,id);
        return "redirect:/pos";
    }

    @PostMapping("/checkout")
    public String checkout(@RequestParam String paymentMethod, @AuthenticationPrincipal CustomUserDetails auth) {
        Order order = orderService.checkout(userService.findById(auth.getId()), paymentMethod);
        cartService.clearCart(auth);
        return "redirect:/pos/receipt/" + order.getId();
        //return "redirect:/pos";
    }

    @GetMapping("/receipt/{id}")
    public String receipt(@AuthenticationPrincipal CustomUserDetails auth, @PathVariable Long id, Model model) {
        model.addAttribute("profile", profileService.checkProfile(auth.getId()));
        model.addAttribute("order", orderService.getById(id));
        return "pos/receipt";
    }
}