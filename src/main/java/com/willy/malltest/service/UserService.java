package com.willy.malltest.service;

import com.willy.malltest.dto.UserDto;
import com.willy.malltest.model.User;

public interface UserService {
    User addUsers(User users);

    UserDto addUsersOAuth2(String id, String name, String email);
    boolean checkIfUsernameExist(String email);
    UserDto login(String email, String password);

    UserDto loginOAuth2(String email);



    User updateLastloginTime(Long id);


}
