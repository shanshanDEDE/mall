package com.willy.malltest.controller;


import com.willy.malltest.dto.UserDto;
import com.willy.malltest.model.User;
import com.willy.malltest.service.MailService;
import com.willy.malltest.service.MailServiceImpl;
import com.willy.malltest.service.UserService;
import com.willy.malltest.service.UserServiceImpl;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Date;
import java.util.Objects;

@RestController
@RequestMapping("/user")
@CrossOrigin(allowCredentials = "true", origins = { "http://localhost:5173/", "http://127.0.0.1:5173" })
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private MailService mailService;


    @RequestMapping("/login")
    public UserDto login(
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            HttpSession session) {

        UserDto result = userService.login(email, password);

        if(result != null) {
            userService.updateLastloginTime(result.getUserID());
            session.setAttribute("loggedInUser", result);
        }else {
            throw new RuntimeException("登入失敗，帳號或密碼錯誤");
        }

        return result;
    }

    @RequestMapping("/logout")
    public boolean logout(HttpSession session) {
        session.invalidate();
        return true;
    }

    @PostMapping("/register")
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
            newUsers.setUsername(username);
            newUsers.setEmail(email);
            newUsers.setPhone(phone);
            newUsers.setPassword(password);
            newUsers.setAuthentication(1);

            Date today = new Date();
            newUsers.setRegisterDate(today);
            newUsers.setLastLoginTime(today);

            userService.addUsers(newUsers);
            return newUsers;
        }
    }
    @RequestMapping("/check")
    public boolean checkLogin(HttpSession session) {
        UserDto loggedInMember = (UserDto) session.getAttribute("loggedInMember");

        return !Objects.isNull(loggedInMember);
    }

    @RequestMapping("/mail/pwd")
    public void sendPassword(String receiver) {
        mailService.sendPassword(receiver);



    }

}
