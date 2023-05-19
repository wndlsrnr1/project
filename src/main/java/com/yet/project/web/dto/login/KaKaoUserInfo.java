package com.yet.project.web.dto.login;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class KaKaoUserInfo {
    @JsonProperty("id")
    Long id;
    @JsonProperty("connected_at")
    LocalDateTime connectedAt;
    @JsonProperty("has_email")
    Boolean hasEmail;
    @JsonProperty("kakao_account")
    KakaoAccount kakaoAccount;


}
