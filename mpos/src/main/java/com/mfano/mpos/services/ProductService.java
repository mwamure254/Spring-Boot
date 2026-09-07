package com.mfano.mpos.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mfano.mpos.models.Product;
import com.mfano.mpos.repositories.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepo;

    public void create(Product product) {
        productRepo.save(product);
    }
    
     public Product getById(long id) {
        return productRepo.findById(id).orElse(null);
    }

    public List<Product> findAll() {
        return productRepo.findAll();
    }
}
