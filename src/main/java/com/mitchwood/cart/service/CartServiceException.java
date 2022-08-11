package com.mitchwood.cart.service;

/**
 * This exception, or subclasses of it, are thrown when the CartService gets into trouble.
 *
 */
public class CartServiceException extends RuntimeException {

    public CartServiceException(String msg, Throwable t) {
        super(msg, t);
    }

    public CartServiceException(String msg) {
        super(msg);
    }
}
