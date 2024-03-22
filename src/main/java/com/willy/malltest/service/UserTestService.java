package com.willy.malltest.service;

import com.willy.malltest.model.User;
import com.willy.malltest.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserTestService {
    @Autowired
    private UsersRepository userRepo;

    public String findUserById(long i) {

        User user = userRepo.findById(i).get();
        return user.toString();

    }
}
