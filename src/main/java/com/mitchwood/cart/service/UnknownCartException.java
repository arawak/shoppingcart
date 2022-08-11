package com.mitchwood.cart.service;

import java.util.UUID;

import lombok.NonNull;

/**
 * Thrown when a cart is specified but it doesn't exist.
 */
public class UnknownCartException extends CartServiceException {
    
    public UnknownCartException(UUID cartId) {
        super("no such cartId: " + cartId);
    }
}
