package com.mfano.mpos.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.mfano.mpos.dtos.GlobalSearchResult;
import com.mfano.mpos.inventory.TransferRepository;
import com.mfano.mpos.repositories.ProductRepository;
import com.mfano.mpos.repositories.security.RoleRepository;
import com.mfano.mpos.repositories.security.UserRepository;
import com.mfano.mpos.repositories.BranchRepository;

@Service
public class GlobalSearchService {
        private final ProductRepository productRepository;
        private final UserRepository userRepository;
        private final BranchRepository storeRepository;
        private final RoleRepository roleRepository;
        private final TransferRepository transferRepository;

        public GlobalSearchService(ProductRepository productRepository, UserRepository userRepository,
                        BranchRepository storeRepository, RoleRepository roleRepository,
                        TransferRepository transferRepository) {
                this.productRepository = productRepository;
                this.userRepository = userRepository;
                this.storeRepository = storeRepository;
                this.roleRepository = roleRepository;
                this.transferRepository = transferRepository;
        }

        public List<GlobalSearchResult> search(String query) {

                List<GlobalSearchResult> results = new ArrayList<>();

                if (query == null || query.trim().length() < 2) {
                        return results;
                }

                String keyword = query.trim();

                productRepository.findByNameContainingIgnoreCaseOrSkuContainingIgnoreCase(
                                                keyword, keyword)
                                .forEach(product -> results.add(
                                                new GlobalSearchResult(
                                                                "Product",
                                                                product.getName(),
                                                                "SKU: " + product.getSku(),
                                                                "/inventory/products#" + product.getId())));

                userRepository.findByFinContainingIgnoreCaseOrLanContainingIgnoreCase(
                                                keyword, keyword)
                                .forEach(user -> results.add(
                                                new GlobalSearchResult(
                                                                "User",
                                                                user.getFin() + " " + user.getLan(),
                                                                user.getEmail(),
                                                                "/admin/users#" + user.getId())));

                storeRepository.findByNameContainingIgnoreCase(keyword)
                                .forEach(store -> results.add(
                                                new GlobalSearchResult(
                                                                "Store",
                                                                store.getName(),
                                                                "Store ID: " + store.getId(),
                                                                "/branches#" + store.getId())));

                roleRepository.findByNameContainingIgnoreCase(keyword)
                                .forEach(role -> results.add(
                                                new GlobalSearchResult(
                                                                "Role",
                                                                role.getName(),
                                                                "Role ID: " + role.getId(),
                                                                "/admin/roles#" + role.getId())));

                transferRepository.findByTransferNumberContainingIgnoreCase(keyword)
                                .forEach(transfer -> results.add(
                                                new GlobalSearchResult(
                                                                "Transfer",
                                                                transfer.getTransferNumber(),
                                                                "Transfer ID: " + transfer.getId(),
                                                                "/inventory/transfers#" + transfer.getId())));

                return results;
        }
}
