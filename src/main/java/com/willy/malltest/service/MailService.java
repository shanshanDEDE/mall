package com.willy.malltest.service;



public interface MailService {
    void sendPassword(String email, String phone);

    void sendVerifyCode(String email, String verificationCode);
}
