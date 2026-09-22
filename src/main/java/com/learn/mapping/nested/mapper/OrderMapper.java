package com.learn.mapping.nested.mapper;

import com.learn.mapping.basic.entity.Address;
import com.learn.mapping.nested.dto.OrderDto;
import com.learn.mapping.nested.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(
        componentModel = "spring",
        // Specify the OrderItemMapper for nested mapping
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

    /**
     *
     * MapStruct sees this as a valid custom mapping method because:
     * • parameter type (source): Order.OrderStatus
     * • return type (target): String
     * Method name doesn't make any impact on MapStruct's ability to use it for mapping.
     *
     * The only time the name matters is when you explicitly use it through qualifiers,
     * e.g. @Named("mapOrderStatus") and -> @Mapping(qualifiedByName = "mapOrderStatus")
     */
    default String mapOrderStatus(Order.OrderStatus status) {
        return status != null ? status.name() : null;
    }

    // in compile-time mapstruct would fail with "ambiguous mapping method" error.
//    default String mapOrderStatus2(Order.OrderStatus status) {
//        return "null";
//    }

}
