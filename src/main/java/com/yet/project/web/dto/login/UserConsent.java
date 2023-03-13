package com.yet.project.web.dto.login;

import lombok.Data;

@Data
public class UserConsent {
    Long id;
    Long uid;
    Integer cid;
    Boolean consent;
}
