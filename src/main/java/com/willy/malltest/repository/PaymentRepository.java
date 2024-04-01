package com.willy.malltest.repository;


import com.willy.malltest.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Payment findByEmail(String email);

}
