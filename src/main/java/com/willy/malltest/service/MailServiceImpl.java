package com.willy.malltest.service;

import com.willy.malltest.model.User;
import com.willy.malltest.repository.UsersRepository;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.Random;


@Service
public class MailServiceImpl implements MailService{

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder pwdEncoder;

    @Override
    public void sendPassword(String email, String phone) {



        User user = usersRepository.findByEmailAndPhone(email, phone);

        if (user==null){
            return;
        }

        //////////////////////////////////////////////

        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        int length = 10;

        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            // 從字符集合中隨機選擇一個字符，並將其附加到結果字串中
            char randomChar = characters.charAt(random.nextInt(characters.length()));
            stringBuilder.append(randomChar);
        }

        String newPwd = stringBuilder.toString();
        String content = "親愛的用戶您好，您的臨時密碼為： "+newPwd+" ，請盡速登入系統並更改密碼以確保帳戶安全。";
        String subject = "Apple Tree 忘記密碼 - 新密碼通知";
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject(subject);
        message.setText(content);
        message.setFrom("Apple Tree<project.3c.mall@gmail.com>");


        mailSender.send(message);
        //////////////////////////////////////////////
        String encodedPwd = pwdEncoder.encode(newPwd); // 加密
        user.setPassword(encodedPwd);
        usersRepository.save(user);

    }

    @Override
    public void sendVerifyCode(String email, String verificationCode) {
        System.out.println("VerificationCode:"+verificationCode);
        String content = "歡迎加入Apple Tree 會員，您的驗證碼為："+verificationCode+"，請於10分鐘內輸入以完成註冊，10分鐘後此驗證碼將會失效！";
        String subject = "Apple Tree 註冊驗證碼";
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject(subject);
        message.setText(content);
        message.setFrom("Apple Tree<project.3c.mall@gmail.com>");

        mailSender.send(message);
    }
}
