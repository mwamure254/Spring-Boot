package com.mfano.mpos.dtos.response;

import java.math.BigDecimal;

public record ProductResponse(Long id, String name, BigDecimal price) {}
