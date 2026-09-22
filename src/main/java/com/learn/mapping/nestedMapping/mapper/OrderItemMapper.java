package com.learn.mapping.nestedMapping.mapper;

import com.learn.mapping.nestedMapping.dto.OrderItemDto;
import com.learn.mapping.nestedMapping.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {

    // Map nested product details
    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "productName", source = "product.name")
    @Mapping(target = "productSku", source = "product.sku")
    OrderItemDto toDto(OrderItem item);

    List<OrderItemDto> toDtoList(List<OrderItem> items);

}