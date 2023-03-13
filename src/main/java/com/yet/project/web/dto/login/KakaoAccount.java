package com.yet.project.web.dto.login;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class KakaoAccount {
    @JsonProperty("email_needs_agreement")
    Boolean emailNeedsAgreement;
    @JsonProperty("is_email_valid")
    Boolean isEmailValid;
    @JsonProperty("is_email_verified")
    Boolean isEmailVerified;
    @JsonProperty("email")
    String email;
}
