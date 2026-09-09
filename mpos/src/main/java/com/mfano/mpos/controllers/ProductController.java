package com.mfano.mpos.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mfano.mpos.config.CustomUserDetails;
import com.mfano.mpos.dtos.ProductDto;
import com.mfano.mpos.services.ProductService;
import com.mfano.mpos.services.security.ProfileService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ProfileService profileService;

    @GetMapping
    public String products(@AuthenticationPrincipal CustomUserDetails auth, Model model) {
        model.addAttribute("profile", profileService.checkProfile(auth.getId()));
        model.addAttribute("products", productService.findAll());
        model.addAttribute("product", new ProductDto());
        return "pos/products";
    }

    @PostMapping("/save")
    public String save(
            @Valid @ModelAttribute("product") ProductDto productDto,
            BindingResult result,
            RedirectAttributes redirectAttributes,
            @AuthenticationPrincipal CustomUserDetails auth) {

        if (result.hasErrors()) {
            return "redirect:/products";
        }

        productService.create(productDto, auth);

        redirectAttributes.addFlashAttribute(
                "message",
                "Product added successfully."
        );

        return "redirect:/products";
    }

    @GetMapping("/edit/{id}")
    public String edit(
            @PathVariable Long id,
            Model model) {

        model.addAttribute("product", productService.getById(id));
        model.addAttribute("products", productService.findAll());

        return "products/edit";
    }

    @PostMapping("/update/{id}")
    public String update(
            @PathVariable Long id,
            @Valid @ModelAttribute("product") ProductDto productDto,
            BindingResult result,
            RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return "products/edit";
        }

        productService.update(productDto);

        redirectAttributes.addFlashAttribute(
                "success",
                "Product updated successfully."
        );

        return "redirect:/products";
    }

    @PostMapping("/delete/{id}")
    public String delete(
            @PathVariable Long id,
            RedirectAttributes redirectAttributes) {

        productService.deleteById(id);

        redirectAttributes.addFlashAttribute(
                "success",
                "Product deleted successfully."
        );

        return "redirect:/products";
    }

    @PostMapping("/toggle/{id}")
    public String toggle(
            @PathVariable Long id,
            RedirectAttributes redirectAttributes) {

        productService.toggleActive(id);

        redirectAttributes.addFlashAttribute(
                "success",
                "Product status updated."
        );

        return "redirect:/products";
    }
}
