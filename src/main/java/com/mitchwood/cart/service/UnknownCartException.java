package com.mitchwood.cart.service;

import java.util.UUID;

/**
 * Thrown when a cart is specified but it doesn't exist.
 */
public class UnknownCartException extends CartServiceException {
    
    public UnknownCartException(UUID cartId, Throwable t) {
        super("no such cartId: " + cartId, t);
    }
}
