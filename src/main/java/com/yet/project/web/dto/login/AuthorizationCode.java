package com.yet.project.web.dto.login;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AuthorizationCode {
    @JsonProperty("grant_type")
    String grantType = "authorization_code";
    @JsonProperty("client_id")
    String clientId;
    @JsonProperty("redirect_uri")
    String redirectUri;
    @JsonProperty("code")
    String code;
}
