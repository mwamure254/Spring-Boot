package com.mfano.mpos.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mfano.mpos.cart.Cart;
import com.mfano.mpos.config.CustomUserDetails;
import com.mfano.mpos.order.OrderService;
import com.mfano.mpos.services.ProductService;
import com.mfano.mpos.services.security.ProfileService;
import com.mfano.mpos.services.security.UserService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/pos")
@RequiredArgsConstructor
public class PosController {
    private final UserService userService;
    private final ProductService productService;
    private final OrderService orderService;

    private final ProfileService profileService;

    @GetMapping
    public String posPage(@AuthenticationPrincipal CustomUserDetails auth, Model model) {
        model.addAttribute("profile", profileService.checkProfile(auth.getId()));
        model.addAttribute("products", productService.findAll());
        model.addAttribute("cart", new Cart()); // stored in session
        return "pos/index";
    }

    @PostMapping("/cart/add")
    public String addToCart(@RequestParam Long productId, HttpSession session) {
        Cart cart = getCart(session);
        cart.addItem(productService.getById(productId), 1);
        return "redirect:/pos";
    }

    @PostMapping("/checkout")
    public String checkout(@RequestParam String paymentMethod, HttpSession session) {
        Cart cart = getCart(session);
        //OrderResponse order = orderService.createOrder(cart, paymentMethod);
        session.removeAttribute("cart");
        //return "redirect:/pos/receipt/" + order.id();
        return "redirect:/pos";
    }

    @GetMapping("/receipt/{id}")
    public String receipt(@PathVariable Long id, Model model) {
        model.addAttribute("order", orderService.getById(id));
        return "pos/receipt";
    }

    private Cart getCart(HttpSession session) {
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }
        return cart;
    }
}