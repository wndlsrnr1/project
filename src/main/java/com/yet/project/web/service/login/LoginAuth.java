package com.yet.project.web.service.login;

import com.yet.project.domain.user.User;
import com.yet.project.web.dto.login.LoginForm;

public interface LoginAuth {
    User authUserByLoginForm(LoginForm loginForm);
}
