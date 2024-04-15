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
@CrossOrigin(allowCredentials = "true", origins = { "http://localhost:5173/", "http://127.0.0.1:5173" })
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private MailService mailService;


    @PostMapping("/user/login")
    public UserDto login(
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            HttpSession session) {

        UserDto result = userService.login(email, password);

        if(result != null) {
            userService.updateLastloginTime(result.getUserId());
            session.setAttribute("loggedInUser", result);
        }else {
            throw new RuntimeException("登入失敗，帳號或密碼錯誤");
        }

        return result;
    }

    @GetMapping("/user/logout")
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
    @GetMapping("/user/check")
    public boolean checkLogin(HttpSession session) {
        UserDto loggedInUser = (UserDto) session.getAttribute("loggedInUser");

        return !Objects.isNull(loggedInUser);
    }

    @GetMapping("/user/getSession")
    public UserDto getSession(HttpSession session) {

        return (UserDto) session.getAttribute("loggedInUser");
    }

    @PostMapping("/user/mail/pwd")
    public void sendPassword(@RequestParam("email") String email,
                             @RequestParam("phone") String phone) {
        mailService.sendPassword(email, phone);

    }

    @PostMapping("/user/mail/verify")
    public void sendVerifyCode(@RequestParam("email") String email,
                               @RequestParam("verificationCode") String verificationCode) {

        mailService.sendVerifyCode(email, verificationCode);
    }

}
