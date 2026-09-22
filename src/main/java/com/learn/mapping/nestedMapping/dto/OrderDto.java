package com.learn.mapping.nestedMapping.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderDto(
        Long id,
        String orderNumber,
        String customerName,
        String customerEmail,
        String shippingAddress,
        List<OrderItemDto> items,
        BigDecimal subtotal,
        BigDecimal tax,
        BigDecimal total,
        String status,
        LocalDateTime orderDate
) {
}
