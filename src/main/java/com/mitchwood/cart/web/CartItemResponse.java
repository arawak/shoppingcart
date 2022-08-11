package com.mitchwood.cart.web;

import java.util.UUID;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CartItemResponse {
    
    UUID id;
    int quantity;

}
