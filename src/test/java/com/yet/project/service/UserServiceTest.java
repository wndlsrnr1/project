package com.yet.project.service;

import com.yet.project.repository.dao.user.UserDao;
import com.yet.project.domain.user.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserServiceTest {
    @Autowired
    UserDao userService;

    @Test
    void getUserById() {
    }




}