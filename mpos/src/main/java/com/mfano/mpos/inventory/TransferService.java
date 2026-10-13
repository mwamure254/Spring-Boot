package com.mfano.mpos.inventory;

import java.io.IOException;
import java.util.List;

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
public class TransferService {
    private final TransferRepository transferRepository;
    private final CartRepository cartRepository;
    private final UserService userService;
    Transfer transfer, existing, savedTransfer;

    public TransferService(TransferRepository transferRepository, CartRepository cartRepository,
            UserService userService) {
        this.transferRepository = transferRepository;
        this.cartRepository = cartRepository;
        this.userService = userService;
    }

    public Transfer checkout(CustomUserDetails user, String pm, Long tt, RedirectAttributes red) {
        User staff = userService.findById(user.getId());
        Cart cart = cartRepository.findByUser(staff)
                .orElseThrow(() -> new IllegalStateException("Cart not found"));

        if (cart.getItems().isEmpty()) {
            red.addFlashAttribute(
                    "error",
                    "Cannot checkout an empty cart");
        }

        transfer = Transfer.builder()
                .fromStoreId(staff.getBranch().getId())
                .toStoreId(tt)
                .comment(pm)
                .status(OrderStatus.PENDING)
                .build();

        transfer.setCreatedBy(user.getEmail());

        for (CartItem cartItem : cart.getItems()) {
            Product product = cartItem.getProduct();

            if (product.getStockQuantity() < cartItem.getQuantity()) {
                red.addFlashAttribute(
                        "error",
                        "Insufficient stock for product: "
                                + product.getName());
            }

            TransferItem transferItem = TransferItem.builder()
                    .product(cartItem.getProduct())
                    .quantity(cartItem.getQuantity())
                    .unitPrice(cartItem.getProduct().getPrice())
                    .build();

            transfer.addItem(transferItem);
            transfer.setStatus(OrderStatus.COMPLETED);

            // Reduce stock
            product.setStockQuantity(
                    product.getStockQuantity()
                            - cartItem.getQuantity());
        }

        savedTransfer = transferRepository.save(transfer);

        cart.getItems().clear();
        cartRepository.save(cart);

        return savedTransfer;
    }

    public Transfer getById(Long id) {
        return transferRepository.findById(id).orElse(null);
    }

    public List<Transfer> findAll() {
        return transferRepository.findAll();
    }

    public void deleteById(Long id) {
        transferRepository.deleteById(id);
    }

    public void update(Long id, Long tost, String men) throws IOException {
        existing = getById(id);

        if (existing != null) {
            existing.setToStoreId(tost);
            existing.setComment(men);
        }

        transferRepository.save(existing);
    }
}
