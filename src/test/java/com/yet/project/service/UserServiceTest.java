package com.yet.project.service;

import com.yet.project.domain.service.login.AntMatcher;
import com.yet.project.repository.dao.user.UserDao;
import com.yet.project.domain.user.User;
import com.yet.project.web.interceptor.AddPatternConstant;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.AntPathMatcher;

import java.util.Arrays;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class UserServiceTest {
    @Autowired
    UserDao userService;
    @Autowired
    AntMatcher antMatcher;

    @Test
    void getUserById() {
        boolean match = antMatcher.match("/admin/**", "/admin");
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