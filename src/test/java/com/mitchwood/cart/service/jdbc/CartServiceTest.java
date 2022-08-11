package com.mitchwood.cart.service.jdbc;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mitchwood.cart.service.CartItem;
import com.mitchwood.cart.service.CartService;
import com.mitchwood.cart.service.CartServiceException;

@SpringBootTest
class CartServiceTest {
    
    @Autowired
    CartService cartService;

    @DisplayName("getCartItems - invalid cartId returns an empty list")
	@Test
	void getCartItems1() {
	    List<CartItem> items = cartService.getCartItems(UUID.randomUUID());
	    assertThat(items).isEmpty();
	}

    @DisplayName("no error when deleting an invalid itemId from a valid cart")
    @Test
    void removeCartItem1() {
        cartService.removeCartItem(UUID.randomUUID(), UUID.randomUUID());
    }

    @DisplayName("addCartItem throws CartServiceException when invalid itemId is used")
    @Test
    void addCartItem1() {
        
        // clunky, but it's bedtime
        
        Assertions.assertThrows(CartServiceException.class, () -> {
            cartService.addCartItem(UUID.randomUUID(), UUID.randomUUID(), 1);    
        });
    }
}
