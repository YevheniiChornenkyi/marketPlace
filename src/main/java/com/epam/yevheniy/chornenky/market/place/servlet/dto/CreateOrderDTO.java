package com.epam.yevheniy.chornenky.market.place.servlet.dto;

import com.epam.yevheniy.chornenky.market.place.models.Cart;

public class CreateOrderDTO {
    private final String userId;
    private final String address;
    private final String phoneNumber;
    private final Cart cart;

    public CreateOrderDTO(String userId, String address, String phoneNumber, Cart cart) {
        this.userId = userId;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.cart = cart;
    }

    public String getUserId() {
        return userId;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Cart getCart() {
        return cart;
    }
}
