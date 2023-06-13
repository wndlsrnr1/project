package com.yet.project.service;

import com.yet.project.repository.dao.user.UserDao;
import com.yet.project.web.interceptor.AddPatternConstant;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.AntPathMatcher;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class UserServiceTest {
    @Autowired
    UserDao userService;
    @Autowired
    AntPathMatcher antPathMatcher;

    @Test
    void getUserById() {
        boolean match = antPathMatcher.match("/admin/**", "/admin");
        System.out.println("match = " + match);
        AJ.assertThat(match).isEqualTo(true);
    }

    @Test
    void enumTest() {
        String name = AddPatternConstant.ADMIN.name();
        System.out.println("name = " + name);
        String uri = AddPatternConstant.ADMIN.getUri();
        System.out.println("uri = " + uri);
    }




}