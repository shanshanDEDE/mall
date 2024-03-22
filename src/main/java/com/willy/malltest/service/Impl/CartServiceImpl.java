package com.willy.malltest.service.Impl;


import com.willy.malltest.dto.CartItemDto;
import com.willy.malltest.model.*;
import com.willy.malltest.repository.CartItemsRepository;
import com.willy.malltest.repository.UsersRepository;
import com.willy.malltest.service.CartService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private CartItemsRepository cartItemsRepository;

    public CartItems addOneProductToCart(Long userId, String productSpecId) {
        User user = new User(userId);
        ProductSpec productSpec = new ProductSpec(productSpecId);

        CartItems cartItems = cartItemsRepository.findByUserAndProductSpec(user,productSpec);

        if (cartItems != null) {
            cartItems.setQuantity(cartItems.getQuantity() + 1);
        }

        if (cartItems == null) {
            cartItems = new CartItems();
            cartItems.setUser(user);
            cartItems.setProductSpec(productSpec);
            cartItems.setQuantity(1);
        }

        CartItems item = cartItemsRepository.save(cartItems);
        return item;
    }

    // 根據會員ID取得購物車
    public List<CartItemDto> findCartByUserId(Integer userId) {
        List<CartItems> cartItems = cartItemsRepository.findByUser(new User(userId));

        List<CartItemDto> cartItemDtos = cartItems.stream().map(cartItem -> {

            CartItemDto cartItemDto = new CartItemDto();

            ProductSpec productSpec = cartItem.getProductSpec();

            String productName = new Product().getProductName();


            BeanUtils.copyProperties(productSpec, cartItemDto);
            BeanUtils.copyProperties(cartItem, cartItemDto);
            BeanUtils.copyProperties(productName, cartItemDto);

            ProductPhoto productPhoto= new ProductPhoto();

            List<ProductPhoto> productPhotos = productPhoto.getProductSpec().getProductPhotos();
            if (productPhotos != null && productPhotos.size() != 0) {
                ProductPhoto firstPhoto = productPhotos.get(0);
                cartItemDto.setProductPhotoId(firstPhoto.getPhotoID());
            }

            return cartItemDto;
        }).toList();

        return cartItemDtos;

    }



}
