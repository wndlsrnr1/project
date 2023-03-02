package com.yet.project.web.controller;

import com.yet.project.repository.dao.user.UserDao;
import com.yet.project.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class DBConnectionTestController {

    private final UserDao userDao;

    @RequestMapping("/getUser/{uid}")
    @ResponseBody
    String getUserInfo(@PathVariable Long uid) {
        User user = userDao.findUserById(uid);
        return "안녕하세요 이름: " + user.getName() + ", uid: " + user.getUid();
    }
}
