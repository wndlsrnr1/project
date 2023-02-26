package com.yet.project.web.dto.login;

import lombok.Data;

@Data
public class UnregisterForm {
    Long id;
    String password;
    String password2;
    Boolean consent;
}
