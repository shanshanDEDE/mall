package com.willy.malltest.controller;


import com.willy.malltest.dto.UserDto;
import com.willy.malltest.model.User;
import com.willy.malltest.service.MailService;
import com.willy.malltest.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/user")
@CrossOrigin(allowCredentials = "true", origins = {"http://localhost:5173/", "http://127.0.0.1:5173"})
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private MailService mailService;
    private User user;


    @RequestMapping("/login")
    public UserDto login(@RequestParam("email") String email, @RequestParam("password") String password, HttpSession session) {

        UserDto result = userService.login(email, password);

        if (result != null) {
            userService.updateLastloginTime(result.getUserId());
            session.setAttribute("loggedInUser", result);
        } else {
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
    public User register(@RequestParam("name") String username, @RequestParam("email") String email, @RequestParam("phone") String phone, @RequestParam("password") String password) {

        boolean isExist = userService.checkIfUsernameExist(email);

        if (isExist) {
            throw new RuntimeException("此帳號已註冊");

        } else {
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

    @RequestMapping("/getSession")
    public UserDto getSession(HttpSession session) {
        UserDto loggedInUser = (UserDto) session.getAttribute("loggedInUser");

        return loggedInUser;
    }

    @RequestMapping("/mail/pwd")
    public void sendPassword(@RequestParam("email") String email, @RequestParam("phone") String phone) {
        mailService.sendPassword(email, phone);

    }

    @RequestMapping("mail/verify")
    public void sendVerifyCode(@RequestParam("email") String email, @RequestParam("verificationCode") String verificationCode) {

        mailService.sendVerifyCode(email, verificationCode);
    }

    @PostMapping("/registerAdmin")
    public User registerAdmin(@RequestParam("name") String username, @RequestParam("email") String email, @RequestParam("phone") String phone, @RequestParam("password") String password) {

        boolean isExist = userService.checkIfUsernameExist(email);

        if (isExist) {
            throw new RuntimeException("此管理員已存在");

        } else {
            User newUsers = new User();
            newUsers.setUserName(username);
            newUsers.setEmail(email);
            newUsers.setPhone(phone);
            newUsers.setPassword(password);
            newUsers.setAuthentication(0);

            Date today = new Date();
            newUsers.setRegisterDate(today);
            newUsers.setLastLoginTime(today);

            userService.addUsers(newUsers);
            return newUsers;
        }
    }

    @GetMapping("/getAllUsers")
    public List<User> getAllUsers() {

        return userService.getAllUsers();
    }

    @PutMapping("/banUser")
    public User banUser(@RequestParam("id") Long id) {

        return userService.banUser(id);
    }

    @PutMapping("/unbanUser")
    public User unbanUser(@RequestParam("id") Long id) {

        return userService.unbanUser(id);
    }

    @DeleteMapping("/deleteUser")
    public String deleteUser(@RequestParam("id") Long id) {

        return userService.deleteUser(id);
    }
}
