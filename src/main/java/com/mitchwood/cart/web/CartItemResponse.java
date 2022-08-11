package com.mitchwood.cart.web;

import java.util.UUID;

import lombok.Builder;
import lombok.Value;

/**
 * A DTO to handle the JSON response for a CartItem
 */
@Value
@Builder
public class CartItemResponse {
    
    UUID id;
    int quantity;

}
