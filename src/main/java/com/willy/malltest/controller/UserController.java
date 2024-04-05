package com.willy.malltest.controller;


import com.willy.malltest.dto.UserDto;
import com.willy.malltest.model.User;
import com.willy.malltest.service.MailService;
import com.willy.malltest.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Objects;

@RestController

@CrossOrigin(origins = "http://localhost:8081", allowCredentials = "true")

public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private MailService mailService;


    @RequestMapping("/user/login")
    public UserDto login(
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            HttpSession session) {

        UserDto result = userService.login(email, password);

        if(result != null) {
            userService.updateLastloginTime(result.getUserId());
            session.setAttribute("loggedInMember", result);
        }else {
            throw new RuntimeException("登入失敗，帳號或密碼錯誤");
        }

        return result;
    }

    @RequestMapping("/user/logout")
    public boolean logout(HttpSession session) {
        session.invalidate();
        return true;
    }

    @PostMapping("/user/register")
    public User register(
            @RequestParam("name") String username,
            @RequestParam("email") String email,
            @RequestParam("phone") String phone,
            @RequestParam("password") String password){

        boolean isExist = userService.checkIfUsernameExist(email);

        if(isExist) {
            throw new RuntimeException("此帳號已註冊");

        }else {
            User newUsers = new User();
            newUsers.setUserName(username);
            newUsers.setEmail(email);
            newUsers.setPhone(phone);
            newUsers.setPassword(password);
            newUsers.setAuthentication(2);

            Date today = new Date();
            newUsers.setRegisterDate(today);
            newUsers.setLastLoginTime(today);

            userService.addUsers(newUsers);
            return newUsers;
        }
    }
    @RequestMapping("/check")
    public boolean checkLogin(HttpSession session) {
        UserDto loggedInUser = (UserDto) session.getAttribute("loggedInUser");
        return !Objects.isNull(loggedInUser);
    }

    @RequestMapping("/user/getSession")
    public UserDto getSession(HttpSession session) {
        UserDto loggedInUser = (UserDto) session.getAttribute("loggedInUser");

        return loggedInUser;
    }

    @RequestMapping("/user/mail/pwd")
    public void sendPassword(@RequestParam("email") String email,
                             @RequestParam("phone") String phone) {
        mailService.sendPassword(email, phone);

    }

    @RequestMapping("/user/mail/verify")
    public void sendVerifyCode(@RequestParam("email") String email,
                               @RequestParam("verificationCode") String verificationCode) {

        mailService.sendVerifyCode(email, verificationCode);
    }

}
