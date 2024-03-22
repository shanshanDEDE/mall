package com.willy.malltest.controller;

import com.willy.malltest.dto.UserDto;
import com.willy.malltest.model.CartItems;
import com.willy.malltest.service.CartService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shop/api")
@CrossOrigin(allowCredentials = "true", origins = { "http://localhost:5173/", "http://127.0.0.1:5173" })
public class CartController {

    @Autowired
    private CartService cartService;

    @RequestMapping("/product/add/{productId}")
    public CartItems addOneProductToCart(@PathVariable String productSpecId, HttpSession session) {
        UserDto loggedInMember = (UserDto) session.getAttribute("loggedInMember");

        if (loggedInMember == null) {
            throw new RuntimeException("未登入錯誤");
        }

        CartItems addProductToCart = cartService.addOneProductToCart(loggedInMember.getUserId(), productSpecId);

        return addProductToCart;
    }
}
