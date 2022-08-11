package com.mitchwood.cart.service;

import java.util.UUID;

import com.mitchwood.cart.web.CartItemResponse;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CartItem {

    UUID id;
    int quantity;
    
    public CartItemResponse toCartItemResponse() {
        return CartItemResponse.builder()
                .id(id)
                .quantity(quantity)
                .build();
    }
}
