package com.mitchwood.cart.service;

import java.util.List;
import java.util.UUID;

import lombok.NonNull;

/**
 * This service will throw a {@link CartServiceException} if it runs into problems
 * with backing services such as network or database.
 */
public interface CartService {

    /**
     * Returns the requested subset of cart items. 
     * 
     * @param cartId
     * @param page
     * 
     * @return a possibly empty List, but never null
     */
    List<CartItem> getCartItems(@NonNull UUID cartId);

    /**
     * Removes an item from a cart. If the quantity is greater than 1, all instances
     * are removed. This will not fail if the cart does not contain the specified item.  
     * 
     * @param cartId
     * @param itemId
     * 
     * @throws UnknownCartException if the cart doesn't exist 
     */
    void removeCartItem(@NonNull UUID cartId, @NonNull UUID itemId);
    
    /**
     * Adds a given quantity of an item to a cart. 
     *  
     * @param cartId
     * @param itemId
     * @param quantity must be > 0
     * 
     * @throws IllegalArgumentException if the quantity is invalid
     * @throws UnknownCartException if the cart doesn't exist 
     * @throws UnknownItemException if the item doesn't exist 
     */
    void addCartItem(@NonNull UUID cartId, @NonNull UUID itemId, int quantity);

}
