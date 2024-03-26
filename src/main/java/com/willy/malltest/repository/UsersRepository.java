package com.willy.malltest.repository;


import com.willy.malltest.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface UsersRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    User findByUserID(Long id);

}
