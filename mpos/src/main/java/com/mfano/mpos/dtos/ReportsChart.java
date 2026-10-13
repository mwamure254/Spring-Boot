package com.mfano.mpos.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ReportsChart(
        LocalDate date,
        long sales,
        BigDecimal revenue,
        long customers
) {}
