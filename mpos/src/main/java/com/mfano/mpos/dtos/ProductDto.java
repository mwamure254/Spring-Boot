package com.mfano.mpos.dtos;

import java.math.BigDecimal;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    @NonNull 
    private String name;
    private String description;
    private String sku;
    @NonNull 
    private Integer stock;
    @NonNull 
    private BigDecimal price;
    //private String category;
    private MultipartFile image;
}
