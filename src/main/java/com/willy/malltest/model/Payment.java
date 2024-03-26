package com.willy.malltest.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="Payment")
@Getter
@Setter

public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="PaymentId")
    private Long paymentId;

    @Column(name="email")
    private String email;

    @Column(name="amount")
    private double amount;

}
