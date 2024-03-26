package com.willy.malltest.service;

import com.willy.malltest.dto.UserDto;
import com.willy.malltest.model.User;
import com.willy.malltest.repository.UsersRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UsersRepository userRepository;

    @Autowired
    private PasswordEncoder pwdEncoder;

    // 加密後存資料庫
    @Override
    public User addUsers(User users)
    {
        String encodedPwd = pwdEncoder.encode(users.getPassword()); // 加密
        users.setPassword(encodedPwd);
        return userRepository.save(users);
    }
    // check user exist
    @Override
    public boolean checkIfUsernameExist(String email) {
        User dbUser = userRepository.findByEmail(email);
        if(dbUser != null) {
            return true;
        }else {
            return false;
        }
    }
    // check login
    @Override
    public UserDto login(String email, String password)
    {
        User dbUser = userRepository.findByEmail(email);

        if(dbUser!=null)
        {
            // 你要比對的字串, 資料庫裡面的字串
            if (pwdEncoder.matches(password, dbUser.getPassword()))
            {
                UserDto userDto = new UserDto();
                BeanUtils.copyProperties(dbUser,userDto);

                return userDto;
            }
        }

        return null;
    }

    @Override
    public User updateLastloginTime(Long id) {
        User user = userRepository.findByUserID(id);

        Date currentDate = new Date();
        user.setLastLoginTime(currentDate);
        userRepository.save(user);
        return user;
    }


}
