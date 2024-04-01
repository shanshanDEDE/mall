package com.willy.malltest.controller;

import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.willy.malltest.requestmodels.PaymentInfoRequest;
import com.willy.malltest.service.PaymentService;
import com.willy.malltest.utils.ExtractJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/api/payment/secure")
public class PaymentController {
    private PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService){
        this.paymentService = paymentService;
    }

    @PostMapping("payment-intent")
    public ResponseEntity<String> createPaymentIntent(@RequestBody PaymentInfoRequest paymentInfoRequest) throws StripeException {
        PaymentIntent paymentIntent = paymentService.createPaymentIntent(paymentInfoRequest);
        String paymentStr = paymentIntent.toJson();

        return new ResponseEntity<>(paymentStr, HttpStatus.OK);
    }

    @PutMapping("/payment-complete")
    public ResponseEntity<String> stripePaymentComplete(@RequestHeader(value="Authorization") String token) throws Exception{
        String email = ExtractJWT.payloadJWTExtraction(token,"\"sub\"");
        if (email == null) {
            throw new Exception("User email is missing");
        }
        return paymentService.stripePayment(email);

    }
}
