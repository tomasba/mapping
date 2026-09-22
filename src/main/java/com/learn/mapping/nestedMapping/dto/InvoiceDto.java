package com.learn.mapping.nestedMapping.dto;

import org.mapstruct.Mapping;

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
