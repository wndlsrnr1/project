package com.yet.project.web.dto.login;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class LoginForm {
    //타입 바인딩에 성공한 필드만 validation이 작동한다.
    @Email(message = "{login.email.wrong}")
    @NotEmpty(message = "{login.email.null}")
    String email;


    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,20}$", message = "{login.email.auth.wrong}")
    String password;
}
