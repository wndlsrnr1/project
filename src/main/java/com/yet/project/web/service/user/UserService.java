package com.yet.project.web.service.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yet.project.domain.user.UserKakao;
import com.yet.project.domain.user.User;
import com.yet.project.domain.user.UserSocialLogin;
import com.yet.project.repository.dao.user.UserDao;
import com.yet.project.web.dto.login.BasicJoinForm;
import com.yet.project.web.dto.login.SocialJoinForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserDao userDao;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public void userJoin(BasicJoinForm join) {
        User user = new User();
        user.setName(join.getName());
        user.setPassword(join.getPassword());
        user.setEmail(join.getEmail());
        user.setPhone(join.getPhone());

        //user consent 한번에 저장하도록 함.
        userDao.saveUser(user);
        User insertedUser = userDao.findUserByEmail(user.getEmail());
        Long uid = insertedUser.getUid();
        log.info("uid when saved = {}", uid);

        List<Integer> requiredIDList = join.getRequired();
        List<Integer> optionIDList = join.getOption();
        userDao.saveUserAgreementsRequired(uid, requiredIDList);
        userDao.saveUsersAgreementsOption(uid, optionIDList);
    }

    public void unregisterUser(User unregisterUser) {
        Long uid = unregisterUser.getUid();
        userDao.removeUserById(uid);
    }

    public Map<String, String> getAccessToken(String code, String clint_id, String redirect_uri) throws JsonProcessingException {
        //token 받아 오기
        Map<String, String> result = new HashMap<>();
        String url = "https://kauth.kakao.com/oauth/token";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("grant_type", "authorization_code");
        requestBody.add("client_id", clint_id);
        requestBody.add("redirect_uri", redirect_uri);
        requestBody.add("code", code);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

        //상태 값 마다 처리하기
        if (responseEntity == null || responseEntity.getStatusCode() != HttpStatus.OK) {
            return null;
        }

        String body = responseEntity.getBody();
        JsonNode jsonNode = objectMapper.readTree(body);
        jsonNode.fieldNames().forEachRemaining(key -> result.put(key, jsonNode.get(key).toString()));
        return result;
    }

    /**
     *
     * @param accessToken
     * @return Map
     * @throws JsonProcessingException
     * keys =
     * email_needs_agreement,
     * is_email_valid,
     * is_email_verified,
     * id, connected_at,
     * has_email,
     * email
     */
    public Map<String, String> getUserInfo(String accessToken) throws JsonProcessingException {
        Map<String, String> result = new HashMap<>();

        String url = "https://kapi.kakao.com/v2/user/me";
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(headers), String.class);

        //상태값마다 다르게 처리하기
        if (response.getStatusCode() != HttpStatus.OK) {
            return null;
        }

        JsonNode jsonNode = objectMapper.readTree(response.getBody());
        jsonNode.fieldNames().forEachRemaining(key -> result.put(key, jsonNode.get(key).toString()));
        JsonNode jsonNode1 = objectMapper.readTree(result.get("kakao_account"));
        jsonNode1.fieldNames().forEachRemaining(key -> result.put(key, jsonNode1.get(key).toString()));
        result.remove("kakao_account");
        return result;
    }

    public User findUserKakao(Long kakaoId) {
        UserKakao userByKakaoId = userDao.findUserByKakaoId(kakaoId);
        if (userByKakaoId == null) {
            return null;
        }

        Long uid = userByKakaoId.getUid();
        return userDao.findUserById(uid);
    }

    public void userJoinByKakao(SocialJoinForm socialJoinForm, String kakaoId, String socialTypeStr) {
        User user = new User();
        user.setName(socialJoinForm.getName());
        user.setEmail(socialJoinForm.getEmail());
        user.setPhone(socialJoinForm.getPhone());

        //유저 저장
        userDao.saveUser(user);
        //유저 찾기
        User userByEmail = userDao.findUserByEmail(user.getEmail());
        Long uid = userByEmail.getUid();

        //동의 항목 저장
        userDao.saveUserAgreementsRequired(uid, socialJoinForm.getRequired());
        userDao.saveUsersAgreementsOption(uid, socialJoinForm.getOption());

        //어떤 소셜로그인으로 가입했는 지 저장
        //user_social_login table
        UserSocialLogin userSocialLogin = new UserSocialLogin();
        userSocialLogin.setUid(uid);
        userSocialLogin.setSocialId(2);
        userDao.saveUserBySocialLogin(userSocialLogin);

        //user_kakao table
        UserKakao userKakao = new UserKakao();
        userKakao.setUid(uid);
        Long kakaoIdLong = Long.parseLong(kakaoId);
        userKakao.setKakaoId(kakaoIdLong);
        userDao.saveUserByKakao(userKakao);

    }
}
