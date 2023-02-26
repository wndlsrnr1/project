package com.yet.project.domain.user;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class User {
    Long uid;
    String name;
    String email;
    String phone;
    String password;
    Integer auth;
}
