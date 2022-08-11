package com.mitchwood.cart.service.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mitchwood.cart.service.CartItem;
import com.mitchwood.cart.service.CartService;
import com.mitchwood.cart.service.CartServiceException;
import com.mitchwood.cart.service.UnknownCartException;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * A cart service, implemented with Spring JDBC
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class JdbcCartService implements CartService {

    // note that these two use the same params so we need to keep the order the same
    static final String SQL_UPDATE_CART_ITEM_COUNT = "update cart_items set quantity = quantity + ? where cart_id = ? and item_id = ?";
    static final String SQL_ADD_CART_ITEM = "insert into cart_items(quantity, cart_id, item_id) values(?,?,?)";
    
    
    static final String SQL_DELETE_CART_ITEM = "delete from cart_items where cart_id = ? and item_id = ?";
    
    static final String SQL_GET_CART_ITEMS = "select ci.item_id, i.name from cart_items ci join items i on i.item_id = ci.item_id" 
                                            + " where cart_id = ? order by item_id";

    final JdbcTemplate jdbc;

    RowMapper<CartItem> cartItemRowMapper = new RowMapper<CartItem>() {

        @Override
        public CartItem mapRow(ResultSet rs, int rowNum) throws SQLException {
            return CartItem.builder()
                    .id(UUID.fromString(rs.getString(1)))
                    .quantity(rs.getInt(2))
                    .build();
        }
    };

    @Override
    public List<CartItem> getCartItems(@NonNull UUID cartId) {
        List<CartItem> items = null;

        validateCartExists(cartId);

        try {
            items = jdbc.query(SQL_GET_CART_ITEMS, cartItemRowMapper, cartId);
        } catch (EmptyResultDataAccessException e) {
            items = Collections.emptyList();
        } catch (Exception e) {
            throw new CartServiceException("failed getting items for cartId=" + cartId, e);
        }
        return items;
    }

    @Transactional
    @Override
    public void removeCartItem(@NonNull UUID cartId, @NonNull UUID itemId) {

        validateCartExists(cartId);
        
        Object[] params = {cartId, itemId};
        
        try {
            log.debug("sql={} params=[{}]", SQL_DELETE_CART_ITEM, params);
            jdbc.update("delete from cart_items where cart_id = ? and item_id = ?", params);
        } catch (Exception e) {
            throw new CartServiceException("failed removing itemId=" + itemId + " from cartId=" + cartId, e);
        }
    }

    @Transactional
    @Override
    public void addCartItem(@NonNull UUID cartId, @NonNull UUID itemId, int quantity) {
        // this one is a little tricky, since we'll either need to increment an existing quantity
        // or create a new row.

        Object[] params = {quantity, cartId, itemId};

        try {
            log.debug("sql={} params=[{}]", SQL_UPDATE_CART_ITEM_COUNT, params);
            int rows = jdbc.update(SQL_UPDATE_CART_ITEM_COUNT, params);
            
            if (rows < 1) { // doesn't exist so insert
                log.debug("sql={} params=[{}]", SQL_ADD_CART_ITEM, params);
                jdbc.update(SQL_ADD_CART_ITEM, params);
            }
        } catch (Exception e) {
            throw new CartServiceException("failed adding qty=" + quantity + " of itemId=" + itemId + " to cartId=" + cartId, e);
        }
    }
    
    /**
     * Returns the requested cart, or null if it doesn't exist.
     * 
     * @param cartId
     * 
     * @return null if the cart does not exist
     */
    Cart getCart(@NonNull UUID cartId) {
        
        // TODO implement this (but of course for this exercise I won't so it's always true)
        
        return Cart.builder()
                .id(cartId)
                .build();
    }
    
    /**
     * Called to ensure that a cartId is valid.
     * 
     * @param cartId
     * 
     * @throws UnknownCartException if it doesn't exist
     */
    void validateCartExists(@NonNull UUID cartId) {
        Cart cart = getCart(cartId);
        if (cart == null) {
            throw new UnknownCartException(cartId);
        }
    }
    
}