package com.learn.mapping.nested.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record InvoiceDto(
        String invoiceNumber,
        String customerName,
        String customerEmail,
        String billingAddress,
        BigDecimal orderTotal,
        LocalDateTime orderDate
) {
}
