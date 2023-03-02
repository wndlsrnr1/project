package com.yet.project.repository.mybatismapper.user;

import com.yet.project.domain.user.UserKakao;
import com.yet.project.domain.user.User;
import com.yet.project.domain.user.UserSocialLogin;
import com.yet.project.web.dto.login.Agreement;
import com.yet.project.web.dto.login.UserConsent;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper {

    @Select("select * from users where uid = #{uid}")
    User selectUserByUid(@Param("uid") Long uid);

    @Select("select uid, email, password from users where email = #{email}")
    User selectUserByEmail(String email);

    @Insert("INSERT INTO users (name, email, phone, password, auth) VALUES (#{name}, #{email}, #{phone}, #{password}, #{auth})")
    @Options(useGeneratedKeys = true, keyProperty = "uid")
    Long insertUser(User user);

    @Insert("INSERT INTO users_consent (id, uid, cid, consent) VALUES (#{id}, #{uid}, #{cid}, #{consent})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertUserConsent(UserConsent userConsent);

    @MapKey("id")
    @Select("SELECT consent.id, consent.name, consent.fullNameKor, consent_required.required FROM consent JOIN consent_required ON consent.id = consent_required.id where consent_required.required = 0")
    Map<Integer, Agreement> selectConsentRequireds();

    @MapKey("id")
    @Select("SELECT consent.id, consent.name, consent.fullNameKor, consent_required.required FROM consent JOIN consent_required ON consent.id = consent_required.id where consent_required.required = 1")
    Map<Integer, Agreement> selectConsentOptions();

    @Select("SELECT PHONE FROM USERS WHERE PHONE = #{phone}")
    List<User> selectUsersPhones(String phone);

    @MapKey("id")
    @Select("SELECT consent.id, consent.name, consent.fullNameKor, consent_required.required FROM consent JOIN consent_required ON consent.id = consent_required.id")
    Map<Integer, Agreement> selectAgreements();

    //DELETE FROM table_name WHERE condition;
    @Delete("delete from users where uid = #{uid}")
    void deleteUserById(Long uid);

    @Select("select id, uid, kakao_id from user_kakao where kakao_id=#{kakaoid}")
    UserKakao selectKakaoUserByKakaoId(Long kakaoId);

    @Insert("insert into user_social_login (uid, social_id) values (#{uid}, #{socialId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertUserSocialLogin(UserSocialLogin userSocialLogin);

    @Insert("insert into user_kakao (uid, kakao_id) values (#{uid}, #{kakaoId})")
    void insertUserKakao(UserKakao userKakao);
}
