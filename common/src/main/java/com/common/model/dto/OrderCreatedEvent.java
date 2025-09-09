package com.common.model.dto;

import java.math.BigDecimal;
import java.time.Instant;

public record OrderCreatedEvent(
        String orderId,
        String userId,
        BigDecimal amount,
        Instant createdAt
) {
}
