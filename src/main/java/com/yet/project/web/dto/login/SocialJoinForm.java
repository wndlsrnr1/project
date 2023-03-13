package com.yet.project.web.dto.login;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.List;

@Data
public class SocialJoinForm{
    @Email(message = "{login.email.wrong}")
    @NotEmpty(message = "{login.email.null}")
    String email;

    @NotEmpty(message = "{NotEmpty.name}")
    String name;

    @Pattern(regexp = "^\\d{1,13}$", message = "{Login.phone.join.wrong}")
    String phone;

    List<Integer> required;
    List<Integer> option;
}
