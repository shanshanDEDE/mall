package com.willy.malltest.service;

import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.willy.malltest.repository.PaymentRepository;
import com.willy.malltest.requestmodels.PaymentInfoRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;

public interface PaymentService {
    public PaymentIntent createPaymentIntent(PaymentInfoRequest paymentInfoRequest) throws StripeException;
    public ResponseEntity<String> stripePayment(String email) throws Exception;
}
