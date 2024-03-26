package com.willy.malltest.controller;

import com.willy.malltest.dto.Purchase;
import com.willy.malltest.dto.PurchaseResponse;
import com.willy.malltest.service.CheckoutService;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/checkout")
public class CheckoutController {

    private CheckoutService checkoutService;
    public CheckoutController(CheckoutService checkoutService) {

        this.checkoutService = checkoutService;
    }
    @PostMapping("/purchase")
    public PurchaseResponse placeOrder(@RequestBody Purchase purchase) {

        PurchaseResponse purchaseResponse = checkoutService.placeOrder(purchase);

        return purchaseResponse;
    }
}
