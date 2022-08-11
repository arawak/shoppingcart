package com.mitchwood.cart.service.jdbc;

import java.util.UUID;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Cart {

    UUID id;
}
