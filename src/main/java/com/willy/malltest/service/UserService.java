package com.willy.malltest.service;

import com.willy.malltest.dto.UserDto;
import com.willy.malltest.model.User;

public interface UserService {
    User addUsers(User users);
    boolean checkIfUsernameExist(String email);
    UserDto login(String email, String password);

    User updateLastloginTime(Long id);

}
