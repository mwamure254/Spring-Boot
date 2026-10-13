package com.mfano.mpos.dtos;

import java.math.BigDecimal;

public record PaymentMethodReport(
        String paymentMethod,
        Long sales,
        BigDecimal revenue
) {}
