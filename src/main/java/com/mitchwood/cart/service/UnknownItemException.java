package com.mitchwood.cart.service;

import java.util.UUID;

/**
 * Thrown when a cart is specified but it doesn't exist.
 */
public class UnknownItemException extends CartServiceException {
    
    public UnknownItemException(UUID itemId, Throwable t) {
        super("no such itemId: " + itemId, t);
    }
}
