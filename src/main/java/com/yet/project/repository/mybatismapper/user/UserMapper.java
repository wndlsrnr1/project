package com.yet.project.repository.mybatismapper.user;

import com.yet.project.domain.user.User;
import com.yet.project.web.dto.login.Agreement;
import com.yet.project.web.dto.login.UserConsent;
import lombok.extern.java.Log;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper {

    @Select("select * from users where uid = #{uid}")
    User findById(@Param("uid") Long uid);

    @Select("select uid, email, password from users where email = #{email}")
    User findByEmail(String email);

    @Select("select uid from users where email = #{email}")
    User findUidByEmail(String email);

    @Insert("INSERT INTO users (name, email, phone, password, auth) VALUES (#{name}, #{email}, #{phone}, #{password}, #{auth})")
    @Options(useGeneratedKeys = true, keyProperty = "uid")
    Long insertUser(User user);

    @Insert("INSERT INTO users_consent (id, uid, cid, consent) VALUES (#{id}, #{uid}, #{cid}, #{consent})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertUserConsent(UserConsent userConsent);

    @Select("select * from users where uid = #{uid}")
    List<UserConsent> findConsentByUserId(@Param("uid") Long uid);

    @MapKey("id")
    @Select("SELECT consent.id, consent.name, consent.fullNameKor, consent_required.required FROM consent JOIN consent_required ON consent.id = consent_required.id where consent_required.required = 0")
    Map<Integer, Agreement> findOptionalAgreement();

    @MapKey("id")
    @Select("SELECT consent.id, consent.name, consent.fullNameKor, consent_required.required FROM consent JOIN consent_required ON consent.id = consent_required.id where consent_required.required = 1")
    Map<Integer, Agreement> findRequiredAgreement();

    @Select("SELECT PHONE FROM USERS WHERE PHONE = #{phone}")
    List<User> findByPhone(String phone);

    @MapKey("id")
    @Select("SELECT consent.id, consent.name, consent.fullNameKor, consent_required.required FROM consent JOIN consent_required ON consent.id = consent_required.id")
    Map<Integer, Agreement> findAllAgreements();

    @MapKey("id")
    @Select("SELECT consent.id, consent.name, consent.fullNameKor, consent_required.required FROM consent JOIN consent_required ON consent.id = consent_required.id where consent_required.required = 1")
    Map<Integer, String> findAllAgreementNames();

    //DELETE FROM table_name WHERE condition;
    @Delete("delete from users where uid = #{uid}")
    void deleteUserById(Long uid);
}
