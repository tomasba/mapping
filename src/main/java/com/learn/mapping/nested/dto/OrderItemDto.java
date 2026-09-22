package com.learn.mapping.nested.dto;

import java.math.BigDecimal;

public record OrderItemDto(
        Long productId,
        String productName,
        String productSku,
        int quantity,
        BigDecimal unitPrice,
        BigDecimal lineTotal
) {
}