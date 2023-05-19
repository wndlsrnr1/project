package com.yet.project.web.dto.login;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
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
