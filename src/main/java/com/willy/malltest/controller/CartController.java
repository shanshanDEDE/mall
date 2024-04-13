package com.willy.malltest.controller;

import com.willy.malltest.dto.CartDto;
import com.willy.malltest.dto.UserDto;
import com.willy.malltest.model.CartItems;
import com.willy.malltest.model.User;
import com.willy.malltest.service.CartService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class CartController{
    @Autowired
    CartService cartService;

    @PostMapping("/cart/add")
    public CartItems addToCart(@RequestParam String specId, @RequestParam int quantity,HttpSession session) {
//
//        @RequestParam String specId,@RequestParam Long userId
        UserDto loggedInUser = (UserDto) session.getAttribute("LoggedInUser");
        if (loggedInUser == null) {
            throw new RuntimeException("Please log in.");
        }
      CartItems addToCart = cartService.addToCart(loggedInUser.getUserId(),specId, quantity);
        return addToCart;
    }

    @GetMapping("/cart")
    public List<CartDto> getCartByUserId(HttpSession session) {
//        @PathVariable Long userId
        UserDto loggedInUser = (UserDto) session.getAttribute("loggedInUser");
        List<CartDto> cart = cartService.findCartByUserId(loggedInUser.getUserId());
        return cart;
    }

    @DeleteMapping("/cart/item/{cartItemId}")
    public ResponseEntity<?> deleteCartItem(@PathVariable Integer cartItemId, HttpSession session) {
        UserDto loggedInUser = (UserDto) session.getAttribute("loggedInUser");

        if (loggedInUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Please log in!");
        }

        try {
            boolean isDeleted = cartService.deleteCartItem(cartItemId);
            if (isDeleted) {
                return ResponseEntity.ok().body("deleted successfully");
            } else {
                // Log the error or handle the case where deletion was unsuccessful
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("delete failed");
            }
        } catch (Exception e) {
            // Log the exception details
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("server error");
        }
    }
    @PutMapping("/cart/{cartItemId}")
    public ResponseEntity<String> updateCartItemQuantity(@PathVariable Integer cartItemId, @RequestBody CartDto cartDto) {
        cartDto.setCartItemId(cartItemId); // 确保DTO中的cartItemId正确设置
        cartService.updateCartItemQuantity(cartDto);
        return ResponseEntity.ok("Cart item quantity updated successfully.");
    }
    @DeleteMapping("/cart/clear/{userId}")
    public ResponseEntity<?> clearCart(@PathVariable Long userId) {
        try {
            cartService.clearCartByUserId(userId);
            return ResponseEntity.ok("Cart cleared successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to clear cart: " + e.getMessage());
        }
    }
}
