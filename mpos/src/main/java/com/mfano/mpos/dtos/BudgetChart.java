package com.mfano.mpos.dtos;

import java.math.BigDecimal;

public record BudgetChart (
        String category,
        BigDecimal allocatedBudget,
        BigDecimal actualSpending
) {}