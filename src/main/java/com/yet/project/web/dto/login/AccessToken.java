package com.yet.project.web.dto.login;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AccessToken {
    //keys = {id_token, token_type, access_token, expires_in};
    @JsonProperty("id_token")
    String idToken;
    @JsonProperty("token_type")
    String tokenType;
    @JsonProperty("access_token")
    String accessToken;
    @JsonProperty("expires_in")
    String expiresIn;

}
