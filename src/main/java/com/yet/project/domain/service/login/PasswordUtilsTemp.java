package com.yet.project.domain.service.login;

import org.springframework.stereotype.Component;

@Component
public class PasswordUtilsTemp implements PasswordUtils {
    @Override
    public String encode(String password) {
        return password;
    }

    @Override
    public String decode(String encodedPassword) {
        return encodedPassword;
    }

    @Override
    public boolean matches(String password, String encodedPassword) {
        return password.equals(decode(encodedPassword));
    }
}
