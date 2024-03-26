package com.willy.malltest.requestmodels;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentInfoRequest {
    private int amount;
    private String currency;
    private String receiptEmail;
}
