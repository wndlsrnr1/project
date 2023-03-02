package com.yet.project.web.dto.login;

import lombok.Data;

import javax.validation.constraints.*;
import java.util.List;

@Data
public class BasicJoinForm {

    @Email(message = "{login.email.wrong}")
    @NotEmpty(message = "{login.email.null}")
    String email;

    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,20}$", message = "{Login.email.join.wrong}")
    String password;
    String password2;

    @NotEmpty(message = "{NotEmpty.name}")
    String name;

    @Pattern(regexp = "^\\d{1,13}$", message = "{Login.phone.join.wrong}")
    String phone;

    List<Integer> required;
    List<Integer> option;



}
