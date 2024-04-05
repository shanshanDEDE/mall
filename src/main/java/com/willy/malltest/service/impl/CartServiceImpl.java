package com.willy.malltest.service.impl;

import com.willy.malltest.dto.CartDto;
import com.willy.malltest.model.*;
import com.willy.malltest.repository.CartRepository;
import com.willy.malltest.repository.ProductPhotoRepository;
import com.willy.malltest.repository.ProductRepository;
import com.willy.malltest.repository.ProductSpecRepository;
import com.willy.malltest.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ProductSpecRepository productSpecRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductPhotoRepository productPhotoRepository;
    @Override
    public CartItems addToCart(Long userId, String specId){
        User user = new User(userId);
        ProductSpec spec = new ProductSpec(specId);

        CartItems cartItems = cartRepository.findByUserAndProductSpec(user,spec);
        if (cartItems != null){
            cartItems.setQuantity(cartItems.getQuantity()+1);
        }
        if (cartItems == null){
            cartItems = new CartItems();
            cartItems.setQuantity(1);
            cartItems.setUser(user);
            cartItems.setProductSpec(spec);
        }
        CartItems saveCartItems = cartRepository.save(cartItems);
        return saveCartItems;

    }

    @Override
    public List<CartDto> findCartByUserId(Long userId) {
        List<CartItems> cartItems = cartRepository.findCartByUser(new User(userId));
        List<CartDto> cartDtos = new ArrayList<>();
        for (CartItems cartItem : cartItems) {
            CartDto cartDto = new CartDto();
            ProductSpec productSpec = productSpecRepository.findProductSpecBySpecId(cartItem.getProductSpec().getSpecId());
            Product product = productRepository.findById(productSpec.getProduct().getProductId()).orElse(null);
            if (product != null) {
                cartDto.setProductName(product.getProductName());
                cartDto.setProductPrice(product.getPrice());
            }
            cartDto.setCartItemId(cartItem.getCartItemId());
            cartDto.setSpecId(cartItem.getProductSpec().getSpecId());
            cartDto.setQuantity(cartItem.getQuantity());
            List<ProductPhoto> productPhotos= productPhotoRepository.findProductPhotoByProductSpec(productSpec);
            if (productPhotos != null && productPhotos.size() != 0) {
                 ProductPhoto productPhoto = productPhotos.get(0);
                 cartDto.setProductPhotoId(productPhoto.getPhotoId());
                 cartDto.setPhotoFile(productPhoto.getPhotoFile());
            }
            cartDtos.add(cartDto);
        }
        return cartDtos;
    }
    @Override
    public boolean deleteCartItem(Integer cartItemId) {
        cartRepository.deleteById(cartItemId);
        return true;
    }
    @Override
    public void updateCartItemQuantity(CartDto cartDto) {
        Optional<CartItems> optionalCartItem = cartRepository.findById(cartDto.getCartItemId());
        if (optionalCartItem.isPresent()) {
            CartItems cartItem = optionalCartItem.get();
            cartItem.setQuantity(cartDto.getQuantity());
            cartRepository.save(cartItem);
        }
    }
}
