package com.yet.project.repository.dao.user;

import com.yet.project.domain.user.UserKakao;
import com.yet.project.domain.user.User;
import com.yet.project.domain.user.UserSocialLogin;
import com.yet.project.web.dto.login.Agreement;

import java.util.List;
import java.util.Map;

public interface UserDao {
    User findUserById(Long id);

    User findUserByEmail(String email);

    Map<Integer, Agreement> findAgreementRequiredMap();

    Map<Integer, Agreement> findAgreementOptionMap();

    Map<Integer, Agreement> findAllAgreements();

    List<User> findUsersByPhone(String phone);

    Long saveUser(User user);

    void saveUserAgreementsRequired(Long uid, List<Integer> requiredIDList);

    void saveUsersAgreementsOption(Long uid, List<Integer> requiredIDList);

    void removeUserById(Long uid);

    UserKakao findUserByKakaoId(Long kakaoId);

    void saveUserBySocialLogin(UserSocialLogin userSocialLogin);

    void saveUserByKakao(UserKakao userKakao);
}
