package com.yet.project.web.dto.login;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthError {
    Integer code;
    String message;
    String requestURI;
}
