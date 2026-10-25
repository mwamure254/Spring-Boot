package com.mfano.mpos.controllers;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Objects;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mfano.mpos.cart.CartService;
import com.mfano.mpos.config.CustomUserDetails;
import com.mfano.mpos.dtos.ProductDto;
import com.mfano.mpos.inventory.Transfer;
import com.mfano.mpos.inventory.TransferItem;
import com.mfano.mpos.inventory.TransferService;
import com.mfano.mpos.models.Product;
import com.mfano.mpos.services.BranchService;
import com.mfano.mpos.services.ProductService;
import com.mfano.mpos.services.security.UserService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/inventory")
public class InventoryController {
    private final UserService userService;
    private final BranchService branchService;
    private final TransferService transferService;
    private final CartService cartService;
    private final ProductService productService;

    public InventoryController(UserService userService, BranchService branchService,
            TransferService transferService, CartService cartService, ProductService productService) {
        this.userService = userService;
        this.branchService = branchService;
        this.transferService = transferService;
        this.cartService = cartService;
        this.productService = productService;
    }

    @GetMapping("/{option}")
    public String dashboard(@AuthenticationPrincipal CustomUserDetails auth, Model model, @PathVariable String option) {
        userService.getPrices(auth, model);

        String dir = "";
        switch (option) {
            case "dashboard":
                dir = "index";
                break;

            case "transfers":
                model.addAttribute("transfers", transferService.findAll());
                for (Transfer t : transferService.findAll()) {
                    model.addAttribute("from", branchService.findById(t.getFromStoreId()));
                    model.addAttribute("to", branchService.findById(t.getToStoreId()));
                }
                model.addAttribute("stores", branchService.findAll());
                model.addAttribute("cart", cartService.getCart(userService.findById(auth.getId())));
                dir = "inventory/transfers";
                break;
            case "products":
                model.addAttribute("products", productService.findAll());
                model.addAttribute("product1", new ProductDto());
                model.addAttribute("product2", new Product());
                dir = "inventory/products";
                break;
        }
        return dir;
    }

    @PostMapping("/products/save")
    public String save(
            @Valid @ModelAttribute("product1") ProductDto productDto,
            BindingResult result,
            RedirectAttributes redirectAttributes,
            @AuthenticationPrincipal CustomUserDetails auth) {

        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute(
                    "error",
                    "Sorry an error occured.");
            return "redirect:/inventory/products";
        }
        if (productService.getBySku(productDto.getSku()) != null) {
            redirectAttributes.addFlashAttribute(
                    "error",
                    "Product SKU already exists.");
            return "redirect:/inventory/products";
        }

        productService.create(productDto, auth);

        redirectAttributes.addFlashAttribute(
                "message",
                "Product added successfully.");

        return "redirect:/inventory/products";
    }

    @PostMapping("/products/update/{id}")
    public String update(
            @PathVariable Long id,
            @Valid ProductDto productDto,
            @RequestParam("image") MultipartFile image,
            RedirectAttributes redirectAttributes) throws IOException {

        productService.update(id, productDto, image);

        redirectAttributes.addFlashAttribute(
                "message",
                "Product updated successfully.");

        return "redirect:/inventory/products";
    }

    @PostMapping("/products/delete/{id}")
    public String deleteProduct(
            @PathVariable Long id,
            RedirectAttributes redirectAttributes) {

        productService.deleteById(id);

        redirectAttributes.addFlashAttribute(
                "message",
                "Product deleted successfully.");

        return "redirect:/inventory/products";
    }

    @PostMapping("/products/toggle/{id}")
    public String toggle(
            @PathVariable Long id,
            RedirectAttributes redirectAttributes) {

        productService.toggleActive(id);

        redirectAttributes.addFlashAttribute(
                "message",
                "Product status updated.");

        return "redirect:/inventory/products";
    }

    @PostMapping("/transfer")
    public String transferItems(@RequestParam String comment, @RequestParam Long branch,
            @AuthenticationPrincipal CustomUserDetails auth, RedirectAttributes red) {
        try {
            transferService.checkout(auth, comment, branch, red);
            cartService.clearCart(auth);
            red.addFlashAttribute(
                    "message",
                    "Items tranfered successfully.");

        } catch (Exception e) {
            red.addFlashAttribute(
                    "error",
                    e.getMessage());
        }
        return "redirect:/inventory/transfers";
    }

    @GetMapping("/transfers/{id}")
    public String viewTransfer(@PathVariable Long id, Model model, @AuthenticationPrincipal CustomUserDetails auth) {
        userService.getPrices(auth, model);

        Transfer transfer = transferService.getById(id);
        // "Transfer not found";

        model.addAttribute("transfer", transfer);
        model.addAttribute("items", transfer.getItems());
        model.addAttribute("from", branchService.findById(transfer.getFromStoreId()));
        model.addAttribute("to", branchService.findById(transfer.getToStoreId()));

        BigDecimal total = transfer.getItems().stream()
                .map(TransferItem::getSubtotal)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        model.addAttribute("total", total);

        return "inventory/transfer-details";
    }

    @PostMapping("/transfers/delete/{id}")
    public String deleteTransfer(
            @PathVariable Long id,
            RedirectAttributes redirectAttributes) {
        try {

            transferService.deleteById(id);
            redirectAttributes.addFlashAttribute(
                    "message",
                    "Transfer deleted successfully.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute(
                    "error",
                    e.getMessage());
        }

        return "redirect:/inventory/transfers";
    }

    @PostMapping("/transfers/update/{id}")
    public String update(
            @PathVariable Long id,
            Transfer t,
            RedirectAttributes redirectAttributes) throws IOException {

        transferService.update(id, t.getToStoreId(), t.getComment());

        redirectAttributes.addFlashAttribute(
                "message",
                "Transfer updated successfully.");

        return "redirect:/inventory/transfers";
    }

}
