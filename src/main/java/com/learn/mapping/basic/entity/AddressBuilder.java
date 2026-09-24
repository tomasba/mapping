package com.learn.mapping.basic.entity;

public class AddressBuilder {
    private Long id;
    private String street;
    private String city;
    private String state;
    private String zipCode;
    private String country;
    private Address.AddressType type;
    private User user;

    public AddressBuilder setId(Long id) {
        this.id = id;
        return this;
    }

    public AddressBuilder setStreet(String street) {
        this.street = street;
        return this;
    }

    public AddressBuilder setCity(String city) {
        this.city = city;
        return this;
    }

    public AddressBuilder setState(String state) {
        this.state = state;
        return this;
    }

    public AddressBuilder setZipCode(String zipCode) {
        this.zipCode = zipCode;
        return this;
    }

    public AddressBuilder setCountry(String country) {
        this.country = country;
        return this;
    }

    public AddressBuilder setType(Address.AddressType type) {
        this.type = type;
        return this;
    }

    public AddressBuilder setUser(User user) {
        this.user = user;
        return this;
    }

    public Address createAddress() {
        return new Address(id, street, city, state, zipCode, country, type, user);
    }
}