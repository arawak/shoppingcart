package com.mitchwood.cart.web;

import java.util.UUID;

import lombok.Data;

@Data
public class CartItemRequest {
    
    UUID id;
    int quantity;

}
