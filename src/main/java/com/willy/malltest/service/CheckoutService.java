package com.willy.malltest.service;

import com.willy.malltest.dto.Purchase;
import com.willy.malltest.dto.PurchaseResponse;
import org.springframework.stereotype.Service;


public interface CheckoutService {
    PurchaseResponse placeOrder(Purchase purchase);
}
