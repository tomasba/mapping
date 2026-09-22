package com.learn.mapping.nested.mapper;

import com.learn.mapping.basic.entity.Address;
import com.learn.mapping.nested.dto.InvoiceDto;
import com.learn.mapping.nested.entity.Customer;
import com.learn.mapping.nested.entity.Order;
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