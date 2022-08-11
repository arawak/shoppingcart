package com.mitchwood.cart.web;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CartControllerTest {
    
    @Autowired
    MockMvc mvc;
    
    @Disabled
    @DisplayName("happy path test for getting cart items")
    public void testGetCartItems() {
        throw new RuntimeException("not yet implemented");
    }
    
    /*
     * here we'd write a bunch of integration tests 
     */

}
