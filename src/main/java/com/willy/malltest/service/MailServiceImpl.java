package com.willy.malltest.service;

import com.willy.malltest.model.User;
import com.willy.malltest.repository.UsersRepository;
import org.springframework.mail.SimpleMailMessage;
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

    @Override
    public void sendPassword(String receiver) {



        User user = usersRepository.findByEmail(receiver);

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
        String content = "你的臨時密碼為： "+newPwd+" ，請盡速登入更改密碼。";
        String subject = "A New Password For You";
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(receiver);
        message.setSubject(subject);
        message.setText(content);
        message.setFrom("XXX商城<xxx@gmail.com>");


        mailSender.send(message);
        //////////////////////////////////////////////

        user.setPassword(newPwd);
        usersRepository.save(user);

    }
}
