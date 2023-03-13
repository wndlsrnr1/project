package com.yet.project.web.dto.login;

import lombok.Data;

@Data
public class Agreement {
    int id;
    String name;
    Boolean required;
    String fullNameKor;
}
