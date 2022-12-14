package com.mitchwood.cart.web;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import com.mitchwood.cart.service.CartItem;
import com.mitchwood.cart.service.CartService;
import com.mitchwood.cart.service.UnknownCartException;
import com.mitchwood.cart.service.UnknownItemException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Controller
@Slf4j
public class CartController {    
    
    final CartService cartService;

    @GetMapping("/cart/{cartId}/items")
    public List<CartItemResponse> getCartItems(@PathVariable UUID cartId) {
        List<CartItem> items = null;
        
        try {
            items = cartService.getCartItems(cartId);
        } catch (Exception e) {
            log.error("error cart items for cartId=" + cartId, e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);            
        }
        
        List<CartItemResponse> response = new ArrayList<>(items.size());
        for (CartItem item : items) {
            response.add(item.toCartItemResponse());
        }
        
        return response;
    }
    
    @PostMapping("/cart/{cartId}/items")
    public void addCartItem(@RequestBody CartItemRequest request, @PathVariable UUID cartId) {
        
        try {
            cartService.addCartItem(cartId, request.getId(), request.getQuantity());
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid quantity specified");
        } catch (Exception e) {
            log.error("error adding request=" + request + " to cartId=" + cartId, e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }        
    }
    
    @DeleteMapping("/cart/{cartId}/items/{itemId}")
    public void removeCartItem(UUID cartId, UUID itemId) {
        
        try {
            cartService.removeCartItem(cartId, itemId);         
        } catch (UnknownCartException e) {
            // FIXME really should return a structure with a machine-parseable error code
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "unknown cart specified");
        } catch (UnknownItemException e) {
            // here too
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "unknown item specified");        
        } catch (Exception e) {
            log.error("error removing itemId=" + itemId + " from cartId=" + cartId, e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
