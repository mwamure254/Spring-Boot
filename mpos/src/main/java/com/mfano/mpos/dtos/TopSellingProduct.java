package com.mfano.mpos.dtos;

import java.math.BigDecimal;

public record TopSellingProduct(
        Long productId,
        String name,
        String sku,
        BigDecimal price,
        Long quantitySold,
        BigDecimal revenue,
        String image
) {}
