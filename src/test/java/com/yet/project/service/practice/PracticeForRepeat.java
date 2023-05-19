package com.yet.project.service.practice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yet.project.web.dto.login.AccessToken;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class PracticeForRepeat {

    //Cammel case로 된 dto를 sanke case로 바꾸어서 응답하기.
    @Test
    void JsonParserTest() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        AccessToken accessToken = new AccessToken();
        accessToken.setAccessToken("asdfasdfasdf");
        ResponseEntity responseEntity = new ResponseEntity(objectMapper.writeValueAsString(accessToken), HttpStatus.OK);
        Assertions.assertThat(responseEntity.toString()).isEqualTo("" +
            "<200 OK OK," +
            "{\"id_token\":null," +
            "\"token_type\":null," +
            "\"access_token\":\"asdfasdfasdf\"," +
            "\"expires_in\":null" +
            "}," +
            "[]>");
    }

}
