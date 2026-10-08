package com.mfano.mpos.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mfano.mpos.config.CustomUserDetails;
import com.mfano.mpos.dtos.ProductDto;
import com.mfano.mpos.models.Product;
import com.mfano.mpos.repositories.ProductRepository;

@Service
public class ProductService {
    private final ProductRepository productRepo;
    private Product product, existing;

    @Value("${app.upload-dir}")
    private String baseDirectory;
    // private String baseDirectory = "src/main/resources/static/image/products/";

    public ProductService(ProductRepository productRepo) {
        this.productRepo = productRepo;
    }

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
        try {
            updateProductImage(id);
            productRepo.deleteById(id);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void toggleActive(Long id) {
        existing = getById(id);
        existing.setActive(!Boolean.TRUE.equals(existing.isActive()));
        save(existing);
    }

    public void create(ProductDto productDto, @AuthenticationPrincipal CustomUserDetails auth) {
        product = new Product();
        // Set product properties from productDto
        product.setBranch(auth.getBranch());
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setStockQuantity(productDto.getStock());
        product.setDescription(productDto.getDescription());
        product.setSku(productDto.getSku());
        if (productDto.getImage() != null && !productDto.getImage().isEmpty()) {
            product.setImage(imagePath(productDto.getImage()));
        }
        productRepo.save(product);
    }

    public void update(Long id, ProductDto productDto, MultipartFile file) throws IOException {
        existing = getById(id);
        existing.setName(productDto.getName());
        existing.setStockQuantity(productDto.getStock());
        existing.setPrice(productDto.getPrice());
        // Only replace image when a new image was selected
        if (file != null && !file.isEmpty()) {
            updateProductImage(id);
            existing.setImage(imagePath(file));
        }
        productRepo.save(existing);
    }

    public String imagePath(MultipartFile file) {
        if (file != null) {
            try {
                // Create profile directory
                Path path = Paths.get(baseDirectory, "products");
                Files.createDirectories(path);
                // Get extension
                String originalName = file.getOriginalFilename();
                String extension = "";

                if (originalName != null && originalName.contains(".")) {
                    extension = originalName.substring(
                            originalName.lastIndexOf(".")).toLowerCase();
                }

                // Generate safe unique filename
                String filename = UUID.randomUUID() + extension;
                Path destination = path.resolve(filename)
                        .toAbsolutePath()
                        .normalize();

                Files.copy(file.getInputStream(), destination,
                        StandardCopyOption.REPLACE_EXISTING);
                return filename;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    // Update Profile Image
    public void updateProductImage(Long id) throws IOException {
        existing = getById(id);
        try {
            String image = existing.getImage();
            Path path = Path.of(baseDirectory, "products", image)
                    .toAbsolutePath()
                    .normalize();
            Files.delete(path);

            existing.setImage(null);
            save(existing);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
