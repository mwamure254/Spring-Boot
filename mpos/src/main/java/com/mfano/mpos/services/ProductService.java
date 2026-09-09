package com.mfano.mpos.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mfano.mpos.config.CustomUserDetails;
import com.mfano.mpos.dtos.ProductDto;
import com.mfano.mpos.models.Product;
import com.mfano.mpos.repositories.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
    private Product product, existing;
    private final ProductRepository productRepo;
    private final String baseDirectory = "src/main/resources/static/image/products/";

    public Product getById(long id) {
        return productRepo.findById(id).orElse(null);
    }

    public List<Product> findAll() {
        return productRepo.findAll();
    }

    public Product save(Product product) {
        return productRepo.save(product);
    }

    public void deleteById(Long id) {
        productRepo.deleteById(id);
    }

    public void toggleActive(Long id) {
        existing = getById(id);
        existing.setActive(!Boolean.TRUE.equals(existing.getActive()));
        save(existing);
    }

    public void create(ProductDto productDto, @AuthenticationPrincipal CustomUserDetails auth) {
        product = new Product();
        // Set product properties from productDto
        product.setBranch(auth.getBranch());
        product.setCreatedBy(auth.getEmail());
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setStockQuantity(productDto.getStock());
        product.setDescription(productDto.getDescription());
        product.setSku(productDto.getSku());
        product.setImage(imagePath(productDto.getImage()));
        productRepo.save(product);
    }

    public void update(ProductDto productDto) {

        product = new Product();
        // Set product properties from productDto
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setStockQuantity(productDto.getStock());
        product.setDescription(productDto.getDescription());
        product.setSku(productDto.getSku());
        product.setImage(imagePath(productDto.getImage()));
        productRepo.save(product);
    }

    public String imagePath(MultipartFile file) {
        if (file != null) {
            try {
                Path path = Path.of(baseDirectory + file.getOriginalFilename());
                Files.createDirectories(path.getParent());
                Files.write(path, file.getBytes());
                return baseDirectory + file.getOriginalFilename();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    // Update Product Image
    public void updateProductImage(Long id, MultipartFile file) throws IOException {
        existing = getById(id);

        String img = file.getOriginalFilename();
        existing.setImage(img);
        save(existing);

    }

    // Update Profile Image
    public void deleteProductImage(Long id, RedirectAttributes red) throws IOException {
        existing = getById(id);
        try {
            String image = existing.getImage();
            Path path = Path.of(baseDirectory + image);
            Files.delete(path);

            existing.setImage(null);
            save(existing);
            red.addFlashAttribute("message", "Profile image deleted successfully");
        } catch (Exception e) {
            red.addFlashAttribute("error", e.getMessage());
        }

    }
}
