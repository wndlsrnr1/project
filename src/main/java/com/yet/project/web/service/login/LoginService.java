package com.yet.project.web.service.login;

import com.yet.project.domain.user.User;
import com.yet.project.repository.dao.user.UserDao;
import com.yet.project.web.dto.login.LoginForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginService implements LoginAuth {

    private final UserDao userDao;
    private final PasswordUtils passwordUtils;

    public User authUserByLoginForm(LoginForm loginForm) {
        String email = loginForm.getEmail();
        String password = loginForm.getPassword();


        User user = userDao.findUserByEmail(email);

        if (user == null) {
            return null;
        }

        if (passwordUtils.matches(password, user.getPassword())) {
            return user;
        }
        return null;
    }
}
