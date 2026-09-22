package com.learn.mapping.nestedMapping.mapper;

import com.learn.mapping.basicMapping.entity.Address;
import com.learn.mapping.nestedMapping.dto.OrderDto;
import com.learn.mapping.nestedMapping.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(
        componentModel = "spring",
        uses = {OrderItemMapper.class}
)
public interface OrderMapper {

    // Complex nested mapping with multiple levels
    @Mapping(target = "customerName", expression = "java(order.getCustomer().getFirstName() + \" \" + order.getCustomer().getLastName())")
    @Mapping(target = "customerEmail", source = "customer.email")
    @Mapping(target = "shippingAddress", source = "shippingAddress", qualifiedByName = "addressToString")
    @Mapping(target = "status", source = "status")
    OrderDto toDto(Order order);

    List<OrderDto> toDtoList(List<Order> orders);

    // Named qualifier for custom mapping
    @Named("addressToString")
    default String addressToString(Address address) {
        if (address == null) return null;
        return String.format("%s, %s, %s %s",
                address.getStreet(),
                address.getCity(),
                address.getState(),
                address.getZipCode()
        );
    }

    default String mapOrderStatus(Order.OrderStatus status) {
        return status != null ? status.name() : null;
    }

}
