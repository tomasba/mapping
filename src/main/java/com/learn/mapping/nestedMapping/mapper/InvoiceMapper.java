package com.learn.mapping.nestedMapping.mapper;

import com.learn.mapping.basicMapping.entity.Address;
import com.learn.mapping.nestedMapping.dto.InvoiceDto;
import com.learn.mapping.nestedMapping.entity.Customer;
import com.learn.mapping.nestedMapping.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface InvoiceMapper {

    // Combine data from multiple sources
    @Mapping(target = "invoiceNumber", source = "order.orderNumber")
    @Mapping(target = "customerName", source = "customer.firstName")
    @Mapping(target = "customerEmail", source = "customer.email")
    @Mapping(target = "billingAddress", source = "billingAddress.street")
    @Mapping(target = "orderTotal", source = "order.total")
    @Mapping(target = "orderDate", source = "order.orderDate")
    InvoiceDto toInvoiceDto(Order order, Customer customer, Address billingAddress);

}