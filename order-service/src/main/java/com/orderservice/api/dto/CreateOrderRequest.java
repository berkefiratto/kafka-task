package com.orderservice.api.dto;

import java.math.BigDecimal;

public record CreateOrderRequest(
        String userId,
        BigDecimal amount
) {
}
