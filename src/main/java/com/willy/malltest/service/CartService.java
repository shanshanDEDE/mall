package com.willy.malltest.service;

import com.willy.malltest.dto.CartItemDto;
import com.willy.malltest.model.CartItems;

import java.util.List;

public interface CartService {
    CartItems addOneProductToCart(Long userId, String productSpecId);
    List<CartItemDto> findCartByUserId(Integer UserId);
}
