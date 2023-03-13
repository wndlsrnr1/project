package com.yet.project.domain.service.login;

public interface PasswordUtils {
    String encode(String password);

    String decode(String encodedPassword);

    boolean matches(String password, String encodedPassword);
}
